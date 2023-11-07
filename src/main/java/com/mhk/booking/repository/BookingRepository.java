package com.mhk.booking.repository;

import com.mhk.booking.BookingStatus;
import com.mhk.booking.models.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.status = 'WAITLIST' AND b.classEndTime < current_timestamp")
    List<Booking> findWaitlistBookingsToRefund();

    @Query("SELECT b FROM Booking b WHERE b.classSchedule.id = :classId AND b.bookingStatus = 'WAITLIST' ORDER BY b.createdAt ASC")
    Booking findFirstWaitlistUserForClass(@Param("classId") Long classId);

}
