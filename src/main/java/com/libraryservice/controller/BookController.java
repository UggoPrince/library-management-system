package com.libraryservice.controller;

import com.libraryservice.dto.request.BookDto;
import com.libraryservice.dto.request.UpdateBookDto;
import com.libraryservice.dto.response.ApiResponse;
import com.libraryservice.exception.ResourceNotFoundException;
import com.libraryservice.model.Book;
import com.libraryservice.service.BookService;
import com.libraryservice.validation.NotEmptyBody;
import com.libraryservice.validation.ValidId;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Book>> createBook(@Valid @RequestBody BookDto bookDto) {
        Book book = bookService.addBook(bookDto);
        ApiResponse<Book> response = new ApiResponse();
        response.setMessage("Book Successfully added.");
        response.setSuccess(true);
        response.setData(book);
        // ApiResponse<Book> response = new ApiResponse<>().B .setMessage("Book Successfully added.
                // .setS , true, book, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Book>>> getBooks() {
        List<Book> books = (List<Book>) bookService.getBooks();
        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setMessage("Books Successfully retrieved.");
        response.setSuccess(true);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getABooks(@ValidId @PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        ApiResponse<Book> response = new ApiResponse<>();
        response.setMessage("Book Successfully retrieved.");
        response.setData(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @ValidId @PathVariable Long id, @Valid @NotEmptyBody @RequestBody UpdateBookDto bookDto) {
        log.info("UPDATE BOOK REQUEST: {}", bookDto.toString());
        Book book = bookService.getBook(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        Book updated = bookService.updateBook(bookDto, book);
        return new ResponseEntity<>(
                new ApiResponse<Book>().successResponse(updated, "Successfully updated"),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@ValidId @PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        bookService.deleteBook(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Book Successfully deleted");
        response.setSuccess(true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
