package com.libraryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "borrowed_books")
public class BorrowedBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("patron")
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @JsonProperty("book")
    @OneToMany
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("borrowedDate")
    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @JsonProperty("returnDate")
    @Column(name = "return_date")
    private LocalDate returnDate;

    public BorrowedBook() {
    }

    public BorrowedBook(Patron patron, Book book, String status, LocalDate borrowedDate, LocalDate returnDate) {
        this.patron = patron;
        this.book = book;
        this.status = status;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }
}
