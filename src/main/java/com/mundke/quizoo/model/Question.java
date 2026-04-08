package com.mundke.quizoo.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Table(name = "quiz_question")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;
}