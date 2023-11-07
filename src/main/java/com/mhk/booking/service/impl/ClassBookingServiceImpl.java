package com.mhk.booking.service.impl;

import com.mhk.booking.BookingStatus;
import com.mhk.booking.models.Booking;
import com.mhk.booking.models.ClassSchedule;
import com.mhk.booking.models.User;
import com.mhk.booking.repository.BookingRepository;
import com.mhk.booking.repository.UserRepository;
import com.mhk.booking.service.ClassBookingService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Component
public class ClassBookingServiceImpl implements ClassBookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;
    Queue<Integer> waitlist = new LinkedList<>();
    public ClassBookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public boolean bookClass(long classId, long userId) {
        if (waitlist.contains(userId)) {
            waitlist.remove(userId);
            System.out.println("Removed from waitlist: User " + userId);
        } else {
            System.out.println("User " + userId + " is not in the waitlist.");
        }
        return true;
    }

    @Override
    public boolean cancelClass(long classId, long userId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(classId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime classStartTime = booking.getClassSchedule().getStartTime();
            if (now.isBefore(classStartTime.minusHours(4))) {
                User user = booking.getUser();
                int classCredits = booking.getClassSchedule().getCreditsRequired();
                user.setUsedCredits(user.getUsedCredits() + classCredits);
                userRepository.save(user);

                booking.setStatus(String.valueOf(BookingStatus.CANCELLED));
                bookingRepository.save(booking);
            } else {
                // Cancellation not allowed within 4 hours of class
                throw new RuntimeException("Cancellation is not allowed within 4 hours of class start time.");
            }
        } else {
            throw new RuntimeException("Booking not found.");
        }
        return true;
    }


    @CachePut(value = "classBookingCache", key = "#classId")
    @Transactional
    public void processClassCancellation(Long classId) {
            if (!waitlist.isEmpty()) {
                int nextUser = waitlist.poll();
                System.out.println("User " + nextUser + " from waitlist is booked.");
            }

    }

    public Booking findFirstWaitlistUserForClass(Long classId) {
        return bookingRepository.findFirstWaitlistUserForClass(classId);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Schedule to run daily at midnight
    public boolean refundCreditsForWaitlist() {
        List<Booking> waitlistBookings = bookingRepository.findWaitlistBookingsToRefund();

        for (Booking booking : waitlistBookings) {
            if (shouldRefundCredits(booking)) {
                refundCredits(booking);
            }
        }
        return true;
    }

    private boolean shouldRefundCredits(Booking booking) {
        if (BookingStatus.WAITLIST.name().equals(booking.getStatus())) {
            return classHasEnded(booking.getClassSchedule());
        }
        return true;
    }

    private boolean classHasEnded(ClassSchedule classSchedule) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime classEndTime = classSchedule.getEndTime();

        return currentTime.isAfter(classEndTime);
    }

    private void refundCredits(Booking booking) {
        booking.setStatus(String.valueOf(BookingStatus.BOOKED));
        bookingRepository.save(booking);

    }


}
