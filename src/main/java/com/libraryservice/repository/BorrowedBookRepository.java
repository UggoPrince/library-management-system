package com.libraryservice.repository;

import com.libraryservice.model.BorrowedBook;
import org.springframework.data.repository.CrudRepository;

public interface BorrowedBookRepository extends CrudRepository<BorrowedBook, Long> {
}
