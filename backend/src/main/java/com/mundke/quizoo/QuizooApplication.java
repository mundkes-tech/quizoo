package com.mundke.quizoo;

import com.mundke.quizoo.model.User;
import com.mundke.quizoo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QuizooApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizooApplication.class, args);
	}

	@Bean
	CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (!userRepository.existsByUsername("admin")) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole("ROLE_ADMIN");
				userRepository.save(admin);
			}

			if (!userRepository.existsByUsername("user")) {
				User user = new User();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("user123"));
				user.setRole("ROLE_USER");
				userRepository.save(user);
			}
		};
	}

}
