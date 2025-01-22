package com.tekarchflightsbooking.tafbookingms.service.interfaces;

import com.tekarchflightsbooking.tafbookingms.models.Bookings;
import com.tekarchflightsbooking.tafbookingms.models.BookingsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BookingService {
    ResponseEntity<Object> addBooking(BookingsDTO booking);

    ResponseEntity<Object> getBookedDetailsById(Long id);

    List<Bookings>  getBookedDetailsByUserId(Long userId);

    void cancelBookingsById(Long id);
}
