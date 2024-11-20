package com.amirul.booking.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean emailVerified;

    @CreationTimestamp // Automatically sets the current timestamp during insertion
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private int credits; // Field to track the user's total credits

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addCredits(int creditsToAdd) {
        this.credits += creditsToAdd;
    }

    public void deductCredits(int creditsToDeduct) {
        if (this.credits >= creditsToDeduct) {
            this.credits -= creditsToDeduct;
        } else {
            throw new IllegalStateException("Not enough credits available.");
        }
    }
}

