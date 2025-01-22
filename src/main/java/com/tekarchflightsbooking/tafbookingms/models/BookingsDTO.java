package com.tekarchflightsbooking.tafbookingms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingsDTO {

    private Long id;
    private Long users_id;
    private Long flights_id;
    private String status; // e.g., Booked, Cancelled
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

