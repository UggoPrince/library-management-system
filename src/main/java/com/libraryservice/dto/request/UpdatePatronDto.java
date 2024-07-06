package com.libraryservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.libraryservice.validation.NotEmptyIfPresent;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePatronDto {
    @NotEmptyIfPresent(message = "Name should not be empty.")
    private String name;

    @NotEmptyIfPresent(message = "Phone Number should not be empty.")
    private String phoneNumber;

    @NotEmptyIfPresent(message = "Email should not be empty.")
    private String email;

    @NotEmptyIfPresent(message = "Address should not be empty.")
    private String address;
}
