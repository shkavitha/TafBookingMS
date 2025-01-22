package com.tekarchflightsbooking.tafbookingms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
private Long id;
   private String username;
   private String email;
   private String phoneNumber;
   private LocalDate createdAt;
   private LocalDate updatedAt;

}
