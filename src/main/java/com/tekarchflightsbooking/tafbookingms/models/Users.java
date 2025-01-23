package com.tekarchflightsbooking.tafbookingms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
private Long id;
   private String username;
   private String email;
   private String phone;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;

}
