package com.mundke.quizoo.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "quiz_option")
@Data
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}