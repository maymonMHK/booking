package com.mhk.booking.quartz.job;

import com.mhk.booking.BookingStatus;
import com.mhk.booking.models.Booking;
import com.mhk.booking.models.WaitingList;
import com.mhk.booking.repository.BookingRepository;
import com.mhk.booking.repository.WaitingListRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveWaitingListJob implements Job {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private WaitingListRepository waitingListRepository;

    private static final int maxSize = 20;

    Long classId = 123L;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        if (bookingRepository.count() == maxSize) {

            Booking waitlistUser = bookingRepository.findFirstWaitlistUserForClass(classId);

            if (waitlistUser != null) {
                waitingListRepository.deleteById(waitlistUser.getId());
                waitlistUser.setStatus(String.valueOf(BookingStatus.BOOKED));
                bookingRepository.save(waitlistUser);
            }
        }

    }


}
