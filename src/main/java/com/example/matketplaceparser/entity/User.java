package com.example.matketplaceparser.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String password;

    private String avatarUrl;
    private boolean isConfirmed;

}
