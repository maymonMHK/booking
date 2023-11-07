package com.mhk.booking.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private String country;
    private LocalDateTime startTime;
    private int requiredCredits;
    private int availableSlots;
    private int maxCapacity;

    @ManyToMany
    @JoinTable(
            name = "class_waitlist",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> waitlist;
    public Class() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }

    public void setRequiredCredits(int requiredCredits) {
        this.requiredCredits = requiredCredits;
    }

    public Class(Long id, String className, String country, LocalDateTime startTime, int requiredCredits) {
        this.id = id;
        this.className = className;
        this.country = country;
        this.startTime = startTime;
        this.requiredCredits = requiredCredits;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<User> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(Set<User> waitlist) {
        this.waitlist = waitlist;
    }

    public Class(Long id, String className, String country, LocalDateTime startTime, int requiredCredits, int availableSlots, int maxCapacity, Set<User> waitlist) {
        this.id = id;
        this.className = className;
        this.country = country;
        this.startTime = startTime;
        this.requiredCredits = requiredCredits;
        this.availableSlots = availableSlots;
        this.maxCapacity = maxCapacity;
        this.waitlist = waitlist;
    }
}
