package com.mhk.booking.repository;

import com.mhk.booking.models.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {


    @Query("SELECT w FROM WaitingList w WHERE w.shouldBeBooked = true")
    List<WaitingList> findEntriesToBook();

    // Custom query to find waitlist entries that need credit refunds
    @Query("SELECT w FROM WaitingList w WHERE w.shouldBeBooked = false")
    List<WaitingList> findEntriesToRefundCredits();
}
