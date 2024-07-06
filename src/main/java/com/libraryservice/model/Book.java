package com.libraryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("author")
    @Column(name = "author")
    private String author;

    @JsonProperty("publicationYear")
    @Column(name = "publication_year")
    private LocalDate publicationYear;

    @JsonProperty("isbn")
    @Column(name = "isbn")
    private String ISBN;

    public Book() {
    }

    public Book(String title, String author, LocalDate publicationYear, String ISBN) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

}


