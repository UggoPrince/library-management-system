package com.libraryservice.service;

import com.libraryservice.dto.request.BookDto;
import com.libraryservice.dto.request.UpdateBookDto;
import com.libraryservice.exception.BadRequestException;
import com.libraryservice.model.Book;
import com.libraryservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookService {
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // get all books
    @Cacheable(value = "books", key = "'books'")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    // get a book
    @Cacheable(value = "book", key = "#id", unless = "#result == null")
    public Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseGet(() -> null);
    }

    // add book
    @Transactional
    @CacheEvict(value = "books", key = "'books'")
    public Book addBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setISBN(bookDto.getISBN());
        book.setPublicationYear(bookDto.getPublicationYear());
        return bookRepository.save(book);
    }

    // edit book
    @Transactional
    @CachePut(value = "book", key = "#book.id")
    public Book updateBook(UpdateBookDto bookDto, Book book) {
        boolean canUpdate = false;
        if (bookDto.getAuthor() != null) {book.setAuthor(bookDto.getAuthor()); canUpdate = true;}
        if (bookDto.getTitle() != null) {book.setTitle(bookDto.getTitle()); canUpdate = true;}
        if (bookDto.getISBN() != null) {book.setISBN(bookDto.getISBN()); canUpdate = true;}
        if (bookDto.getPublicationYear() != null) {
            book.setPublicationYear(LocalDate.parse(bookDto.getPublicationYear()));
            canUpdate = true;
        }
        if (!canUpdate) {
            throw new BadRequestException("Request body is empty.");
        }
        book.setUpdateAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    // delete book
    @CacheEvict(value = "book", key = "#id")
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
