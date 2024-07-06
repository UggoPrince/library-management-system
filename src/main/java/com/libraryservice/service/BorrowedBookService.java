package com.libraryservice.service;

import com.libraryservice.enums.BorrowedBookStatus;
import com.libraryservice.model.Book;
import com.libraryservice.model.BorrowedBook;
import com.libraryservice.model.Patron;
import com.libraryservice.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BorrowedBookService {
    BorrowedBookRepository borrowedBookRepository;

    @Autowired
    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
    }

    public BorrowedBook borrowBook(Patron patron, Book book) {
        BorrowedBook borrowedBook = new BorrowedBook(
                patron, book, BorrowedBookStatus.BORROWED.name(),
                LocalDateTime.now(), LocalDateTime.now());
        return borrowedBookRepository.save(borrowedBook);
    }

    public BorrowedBook returnBook(BorrowedBook borrowedBook) {
        borrowedBook.setStatus(BorrowedBookStatus.RETURNED.name());
        borrowedBook.setReturnedDate(LocalDateTime.now());
        return borrowedBookRepository.save(borrowedBook);
    }

    public BorrowedBook getBorrowedBook(Long patronId, Long bookId) {
        return borrowedBookRepository.findByPatron_IdAndBook_Id(patronId, bookId);
    }

    public BorrowedBook getBorrowedBookByStatus(Long patronId, Long bookId, String status) {
        return borrowedBookRepository.findByPatron_IdAndBook_IdAndStatus(patronId, bookId, status);
    }
}
