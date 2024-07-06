package com.libraryservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.libraryservice.validation.NotEmptyIfPresent;
import com.libraryservice.validation.ValidDateString;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateBookDto {
    @NotEmptyIfPresent(message = "Title should not be empty.")
    private String title;

    @NotEmptyIfPresent(message = "Author should not be empty.")
    private String author;

    @ValidDateString(formats = {"yyyy-MM-dd", "dd/MM/yyyy"}, message = "Publication year must be in the format yyyy-MM-dd or dd/MM/yyyy")
    private String publicationYear;

    @NotEmptyIfPresent(message = "ISBN should not be empty.")
    private String ISBN;
}
