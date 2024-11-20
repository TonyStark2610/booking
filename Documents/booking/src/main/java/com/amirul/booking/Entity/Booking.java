package com.amirul.booking.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Schedule schedule;

    private boolean isWaitlisted;

    private LocalDateTime bookingTime;

    // Constructors, getters, and setters
    public Booking() {}

    public Booking(User user, Schedule schedule, boolean isWaitlisted) {
        this.user = user;
        this.schedule = schedule;
        this.isWaitlisted = isWaitlisted;
        this.bookingTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean isWaitlisted() {
        return isWaitlisted;
    }

    public void setWaitlisted(boolean waitlisted) {
        isWaitlisted = waitlisted;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
