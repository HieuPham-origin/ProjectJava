package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ContactDetail {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
}
