package com.libraryservice.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.libraryservice.util.MultiDateDeserializer;
import com.libraryservice.validation.ValidDateString;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDto {
    @NotEmpty(message = "Title is required.")
    private String title;

    @NotEmpty(message = "Author is required.")
    private String author;

    @NotNull(message = "Publication year is required.")
    // @ValidDateString(formats = {"yyyy-MM-dd", "dd/MM/yyyy"}, message = "Publication year must be in the format yyyy-MM-dd or dd/MM/yyyy")
    @JsonDeserialize(using = MultiDateDeserializer.class)
    private LocalDate publicationYear;

    @NotEmpty(message = "ISBN is required.")
    private String ISBN;
}
