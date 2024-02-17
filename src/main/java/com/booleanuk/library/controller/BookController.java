package com.booleanuk.library.controller;

import com.booleanuk.library.model.Book;
import com.booleanuk.library.repository.BookRepository;
import com.booleanuk.library.response.BookListResponse;
import com.booleanuk.library.response.BookResponse;
import com.booleanuk.library.response.ErrorResponse;
import com.booleanuk.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<BookListResponse> getAllBooks() {
        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.set(this.bookRepository.findAll());
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.set(book);
        return ResponseEntity.ok(bookResponse);
    }
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book movie) {
        Book savedMovie = bookRepository.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book movie = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        bookRepository.delete(movie);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book movieDetails) {
        Book movie = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setYear(movieDetails.getYear());
        movie.setGenre(movieDetails.getGenre());
        movie.setPublisher(movieDetails.getPublisher());
        Book updatedMovie = bookRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }
}
