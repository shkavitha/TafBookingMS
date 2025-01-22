package com.tekarchflightsbooking.tafbookingms.controllers;


import com.tekarchflightsbooking.tafbookingms.models.Bookings;
import com.tekarchflightsbooking.tafbookingms.models.BookingsDTO;
import com.tekarchflightsbooking.tafbookingms.service.BookingServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingServiceImp bookingService;


    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestBody BookingsDTO booking) {
        return bookingService.addBooking(booking);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingsById(@PathVariable("bookingId") Long id) {
        return bookingService.getBookedDetailsById(id);
    }

    @GetMapping("user/{userId}")
    public List<Bookings> getBookingsByUserId(@PathVariable("userId") Long userId) {
        return bookingService.getBookedDetailsByUserId(userId);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBookingsById(@PathVariable("bookingId") Long id) {


        ResponseEntity<Object> bookingDetails = bookingService.getBookedDetailsById(id);

        if (bookingDetails.getStatusCode().is2xxSuccessful()) {
            try {
                bookingService.cancelBookingsById(id);
                return ResponseEntity.ok("Booking cancelled successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to cancel booking ");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking with id " + id + " not found");
        }
    }

}


