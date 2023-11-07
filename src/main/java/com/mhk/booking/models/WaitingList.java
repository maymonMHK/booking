package com.mhk.booking.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class WaitingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private ClassSchedule classSchedule;

    private boolean shouldBeBooked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedToWaitlistAt;

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

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    public boolean isShouldBeBooked() {
        return shouldBeBooked;
    }

    public void setShouldBeBooked(boolean shouldBeBooked) {
        this.shouldBeBooked = shouldBeBooked;
    }

    public Date getAddedToWaitlistAt() {
        return addedToWaitlistAt;
    }

    public void setAddedToWaitlistAt(Date addedToWaitlistAt) {
        this.addedToWaitlistAt = addedToWaitlistAt;
    }

    public WaitingList(Long id, User user, ClassSchedule classSchedule, boolean shouldBeBooked, Date addedToWaitlistAt) {
        this.id = id;
        this.user = user;
        this.classSchedule = classSchedule;
        this.shouldBeBooked = shouldBeBooked;
        this.addedToWaitlistAt = addedToWaitlistAt;
    }
}
