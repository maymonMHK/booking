package com.mhk.booking.controllers;

import com.mhk.booking.service.ClassBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
public class ClassBookingController {

    @Autowired
    ClassBookingService classBookingService;

    @Autowired
    public ClassBookingController(ClassBookingService classBookingService) {
        this.classBookingService = classBookingService;
    }

    @PostMapping("/book/{classId}/{userId}")
    public ResponseEntity<String> bookClass(
            @PathVariable long classId,
            @PathVariable long userId) {
        boolean booked = classBookingService.bookClass(classId, userId);
        if (booked) {
            return ResponseEntity.ok("Class booked successfully.");
        } else {
            return ResponseEntity.badRequest().body("Class booking failed.");
        }
    }

    @PostMapping("/cancel/{classId}/{userId}")
    public ResponseEntity<String> cancelClass(
            @PathVariable long classId,
            @PathVariable long userId) {
        boolean canceled = classBookingService.cancelClass(classId, userId);
        if (canceled) {
            return ResponseEntity.ok("Class canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Class cancellation failed.");
        }
    }


    @PostMapping("/refund-credits-for-waitlist")
    public ResponseEntity<String> refundCreditsForWaitlist() {
        boolean result = classBookingService.refundCreditsForWaitlist();

        if (result) {
            return ResponseEntity.ok("Credits for waitlisted bookings refunded successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to refund credits for waitlisted bookings.");
        }
    }
}
