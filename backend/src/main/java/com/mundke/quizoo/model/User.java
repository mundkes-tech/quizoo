package com.mundke.quizoo.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Table(name = "app_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // ADMIN / USER

    @OneToMany(mappedBy = "user")
    private List<Result> results;
}