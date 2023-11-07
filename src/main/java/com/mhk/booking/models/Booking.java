package com.mhk.booking.models;

import com.mhk.booking.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime bookingTime;

    @ManyToOne
    private User user;

    private String status;
    @ManyToOne
    private Class bookedClass;

    @ManyToOne
    ClassSchedule classSchedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @Column(name = "class_end_time")
    private LocalDateTime classEndTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public Booking(Long id, LocalDateTime bookingTime, User user, Class bookedClass) {
        this.id = id;
        this.bookingTime = bookingTime;
        this.user = user;
        this.bookedClass = bookedClass;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Class getBookedClass() {
        return bookedClass;
    }

    public void setBookedClass(Class bookedClass) {
        this.bookedClass = bookedClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
