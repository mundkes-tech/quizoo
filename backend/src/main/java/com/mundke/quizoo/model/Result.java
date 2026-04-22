package com.mundke.quizoo.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;


@Entity
@Table(name = "quiz_result")
@Data
public class Result {

    private static final DateTimeFormatter DISPLAY_FORMAT =
        DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private LocalDateTime attemptedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Transient
    public String getFormattedAttemptedAt() {
        if (attemptedAt == null) {
            return "-";
        }
        return attemptedAt.format(DISPLAY_FORMAT);
    }
}