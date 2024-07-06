package com.libraryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PatronDto {
    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Address is required.")
    private String address;
}
