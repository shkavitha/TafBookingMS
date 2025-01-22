package com.tekarchflightsbooking.tafbookingms.service;


import com.tekarchflightsbooking.tafbookingms.models.Bookings;
import com.tekarchflightsbooking.tafbookingms.models.BookingsDTO;
import com.tekarchflightsbooking.tafbookingms.models.Flights;
import com.tekarchflightsbooking.tafbookingms.models.Users;
import com.tekarchflightsbooking.tafbookingms.service.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${datastore.booking.service.url}")
    private String dataStore_Booking_Url;

    @Value("${user.service.url}")
    private String user_Url;

    @Value("${flight.service.url}")
    private String flight_Url;


    public void updateAvailability(Long flightId, Flights flight) {
        flight.setAvailable_seats(flight.getAvailable_seats() - 1);
        restTemplate.put(flight_Url + flightId, flight);
    }


    public ResponseEntity<Object> addBooking(BookingsDTO receivedBooking) {
        try {


            Long userId = receivedBooking.getUsers_id();
            Long flightId = receivedBooking.getFlights_id();

            Users user = restTemplate.getForObject(user_Url + userId, Users.class);
            Flights flight = restTemplate.getForObject(flight_Url + flightId, Flights.class);

            if (user != null && flight != null) {
                if (flight.getAvailable_seats() > 0) {
                    Bookings booking = new Bookings();
                    booking.setUsers(user);
                    booking.setFlights(flight);
                    booking.setStatus("Booked");
                    BookingsDTO bookedDetails = restTemplate.postForObject(dataStore_Booking_Url + "book", booking, BookingsDTO.class);
                    updateAvailability(flightId, flight);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Booked Successfully");

                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with id " + flightId + " is full. Please check a different flight");

                }

            } else if (user == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + userId + " not found");
            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with id " + flightId + " not found");
            }
        } catch (Exception e) {
            // Return error response with message and status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Booking creation failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getBookedDetailsById(Long bookingId) {
        try {
            Bookings booking = restTemplate.getForObject(dataStore_Booking_Url + bookingId, Bookings.class);
            if (booking != null) {
                BookingsDTO receivedBookeddetails = new BookingsDTO();
                receivedBookeddetails.setId(booking.getId());
                receivedBookeddetails.setUsers_id(booking.getUsers().getId());
                receivedBookeddetails.setFlights_id(booking.getFlights().getId());
                receivedBookeddetails.setStatus(booking.getStatus());
                receivedBookeddetails.setCreatedAt(booking.getCreatedAt());
                receivedBookeddetails.setUpdatedAt(booking.getUpdatedAt());

                return ResponseEntity.ok(receivedBookeddetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking details with id " + bookingId + " is not found");
            }
        } catch (Exception e) {
            // Return error response with message and status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to retrieve details " + e.getMessage());

        }
    }

    @Override
    public List<Bookings> getBookedDetailsByUserId(Long userId) {
        String url = dataStore_Booking_Url + "users/" + userId;

//try{
        ResponseEntity<List<Bookings>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Bookings>>() {
                }
        );

        return response.getBody();
//}catch (Exception e) {
//    // Return error response with message and status code
//    return null;

    }

    @Override
    public void cancelBookingsById(Long bookingId) {
        try {
            Bookings cancelledBooking = restTemplate.getForObject(dataStore_Booking_Url + bookingId, Bookings.class);
            cancelledBooking.setStatus("Cancelled");
            restTemplate.put(dataStore_Booking_Url + bookingId, cancelledBooking);
        } catch (HttpClientErrorException e) {
            System.out.println("Error: " + e.getStatusCode());
        }
    }

}

