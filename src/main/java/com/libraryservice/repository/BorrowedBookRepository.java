package com.libraryservice.repository;

import com.libraryservice.model.BorrowedBook;
import org.springframework.data.repository.CrudRepository;

public interface BorrowedBookRepository extends CrudRepository<BorrowedBook, Long> {
    BorrowedBook findByPatron_IdAndBook_Id(Long patronId, Long bookId);

    BorrowedBook findByPatron_IdAndBook_IdAndStatus(Long patronId, Long bookId, String status);
}
