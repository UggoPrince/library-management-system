package com.libraryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "borrowed_books")
public class BorrowedBook extends BaseEntity {
    @JsonProperty("patron")
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @JsonProperty("book")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("borrowedDate")
    @Column(name = "borrowed_date")
    private LocalDateTime borrowedDate;

    @JsonProperty("returnedDate")
    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    public BorrowedBook() {
    }

    public BorrowedBook(Patron patron, Book book, String status, LocalDateTime borrowedDate, LocalDateTime returnedDate) {
        this.patron = patron;
        this.book = book;
        this.status = status;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }
}
