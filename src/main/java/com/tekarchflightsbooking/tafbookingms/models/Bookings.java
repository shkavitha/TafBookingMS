package com.tekarchflightsbooking.tafbookingms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {
    private Long id;
    private Users users;
    private Flights flights;
    private String status; // e.g., Booked, Cancelled
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
