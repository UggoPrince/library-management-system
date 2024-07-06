package com.libraryservice.controller;

import com.libraryservice.dto.response.ApiResponse;
import com.libraryservice.enums.BorrowedBookStatus;
import com.libraryservice.exception.BadRequestException;
import com.libraryservice.exception.ResourceNotFoundException;
import com.libraryservice.model.Book;
import com.libraryservice.model.BorrowedBook;
import com.libraryservice.model.Patron;
import com.libraryservice.service.BookService;
import com.libraryservice.service.BorrowedBookService;
import com.libraryservice.service.PatronService;
import com.libraryservice.validation.ValidId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@Validated
public class BorrowedBookController {
    BookService bookService;

    PatronService patronService;

    BorrowedBookService borrowedBookService;

    @Autowired
    public BorrowedBookController(
            BookService bookService,
            PatronService patronService,
            BorrowedBookService borrowedBookService) {
        this.bookService = bookService;
        this.patronService = patronService;
        this.borrowedBookService = borrowedBookService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowedBook>> borrowBook(
            @ValidId @PathVariable Long bookId,
            @ValidId @PathVariable Long patronId) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        Patron patron = patronService.getPatron(patronId);
        if (patron == null) {
            throw new ResourceNotFoundException("Patron not found.");
        }
        BorrowedBook borrowedBook = borrowedBookService
                .getBorrowedBookByStatus(patronId, bookId, BorrowedBookStatus.BORROWED.name());
        if (borrowedBook != null) {
            if (borrowedBook.getStatus().equals(BorrowedBookStatus.BORROWED.name())) {
                throw new BadRequestException("Book already borrowed by Patron.");
            }
        }
        BorrowedBook newBorrowedBook = borrowedBookService.borrowBook(patron, book);
        ApiResponse<BorrowedBook> response = new ApiResponse<>(
                "Book successfully borrowed.",
                true, newBorrowedBook, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowedBook>> returnBook(
            @ValidId @PathVariable Long bookId,
            @ValidId @PathVariable Long patronId) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        Patron patron = patronService.getPatron(patronId);
        if (patron == null) {
            throw new ResourceNotFoundException("Patron not found.");
        }
        BorrowedBook borrowedBook = borrowedBookService
                .getBorrowedBookByStatus(patronId, bookId, BorrowedBookStatus.BORROWED.name());
        if (borrowedBook == null) {
            throw new BadRequestException("Book already returned by Patron.");
        }
        BorrowedBook update = borrowedBookService.returnBook(borrowedBook);
        ApiResponse<BorrowedBook> response = new ApiResponse<>(
                "Book successfully returned.",
                true, update, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
