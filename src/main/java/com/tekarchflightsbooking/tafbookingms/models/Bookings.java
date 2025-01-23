package com.tekarchflightsbooking.tafbookingms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {
    private Long id;
    private Users user;
    private Flights flight;
    private String status; // e.g., Booked, Cancelled
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
