package com.mundke.quizoo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mundke.quizoo.model.Quiz;
import com.mundke.quizoo.model.Result;
import com.mundke.quizoo.model.User;
import com.mundke.quizoo.repository.UserRepository;
import com.mundke.quizoo.service.AuthService;
import com.mundke.quizoo.service.QuizService;
import com.mundke.quizoo.service.ResultService;


@Controller
public class PageController {

    private final QuizService quizService;
    private final ResultService resultService;
    private final AuthService authService;
    private final UserRepository userRepository;

    public PageController(QuizService quizService,
                          ResultService resultService,
                          AuthService authService,
                          UserRepository userRepository) {
        this.quizService = quizService;
        this.resultService = resultService;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            authService.register(user);
            model.addAttribute("message", "Registration successful. Please login.");
            return "login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("username", username);
        model.addAttribute("isAdmin", "ROLE_ADMIN".equals(user.getRole()));
        return "home";
    }

    @GetMapping("/quizzes")
    public String getQuizzes(Model model, Principal principal) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());

        Set<Long> attemptedQuizIds = new HashSet<>();
        if (principal != null) {
            List<Result> results = resultService.getResultsForUser(principal.getName());
            for (Result result : results) {
                if (result.getQuiz() != null && result.getQuiz().getId() != null) {
                    attemptedQuizIds.add(result.getQuiz().getId());
                }
            }
        }
        model.addAttribute("attemptedQuizIds", attemptedQuizIds);

        return "quiz-list";
    }

    @GetMapping("/quiz/{id}")
    public String attemptQuiz(@PathVariable Long id, Model model, Principal principal) {
        Optional<Result> previousResult = resultService.getUserResultForQuiz(principal.getName(), id);
        if (previousResult.isPresent()) {
            model.addAttribute("result", previousResult.get());
            model.addAttribute("alreadyAttempted", true);
            return "result";
        }

        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "attempt";
    }

    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Long quizId,
                            @RequestParam Map<String, String> params,
                            Principal principal,
                            Model model) {
        Map<Long, Long> answers = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("answers[") && key.endsWith("]")) {
                String questionIdStr = key.substring(8, key.length() - 1);
                answers.put(Long.parseLong(questionIdStr), Long.parseLong(entry.getValue()));
            }
        }

        try {
            Result result = resultService.submitQuiz(quizId, answers, principal.getName());
            model.addAttribute("result", result);
            return "result";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            return "result";
        }
    }

    @GetMapping("/results")
    public String viewResults(Principal principal, Model model) {
        List<Result> results = resultService.getResultsForUser(principal.getName());
        model.addAttribute("results", results);
        return "results";
    }

    @GetMapping("/admin/results")
    public String viewAllResults(@RequestParam(required = false) Long quizId, Model model) {
        List<Result> results;
        if (quizId != null) {
            results = resultService.getResultsForQuiz(quizId);
        } else {
            results = resultService.getAllResults();
        }
        model.addAttribute("results", results);
        return "results";
    }

    @GetMapping("/admin/quizzes")
    public String adminQuizzes(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "admin-quizzes";
    }

    @GetMapping("/admin/quizzes/new")
    public String newQuizForm(Model model) {
        Quiz quiz = new Quiz();
        model.addAttribute("quiz", quiz);
        return "admin-quiz-form";
    }

    @GetMapping("/admin/quizzes/edit/{id}")
    public String editQuizForm(@PathVariable Long id, Model model) {
        model.addAttribute("quiz", quizService.getQuizById(id));
        return "admin-quiz-form";
    }

    @PostMapping("/admin/quizzes/save")
    public String saveQuiz(@ModelAttribute Quiz quiz, RedirectAttributes redirectAttributes) {
        if (quiz.getId() == null) {
            quizService.createQuiz(quiz);
            redirectAttributes.addFlashAttribute("message", "Quiz created successfully.");
        } else {
            quizService.updateQuiz(quiz.getId(), quiz);
            redirectAttributes.addFlashAttribute("message", "Quiz updated successfully.");
        }
        return "redirect:/admin/quizzes";
    }

    @PostMapping("/admin/quizzes/delete/{id}")
    public String deleteQuiz(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        quizService.deleteQuiz(id);
        redirectAttributes.addFlashAttribute("message", "Quiz deleted successfully.");
        return "redirect:/admin/quizzes";
    }


}
