package com.mhk.booking.service;

import com.mhk.booking.models.Booking;

public interface ClassBookingService {
    boolean bookClass(long classId, long userId);

    boolean cancelClass(long classId, long userId);

    Booking findFirstWaitlistUserForClass(Long classId);

    boolean refundCreditsForWaitlist();
}
