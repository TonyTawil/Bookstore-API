package com.tony.bookstore;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value="Book management system")
@RestController
public class BookController {

@Autowired
private BookService bookService;

@ApiIgnore
@RequestMapping(value="/")
public void redirect(HttpServletResponse response) throws IOException {
    response.sendRedirect("/swagger-ui.html");
}

// Get all books with pagination and sorting
@ApiOperation(value = "View a list of all books with pagination and sorting")
@GetMapping("/Books")
public ResponseEntity<Page<Book>> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "title") String sortBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction,
        @RequestParam(required = false) String title, // Add title and author as optional request parameters
        @RequestParam(required = false) String author) {

    PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

    if (title != null) {
        return new ResponseEntity<>(bookService.searchBooksByTitle(title, pageable), HttpStatus.OK); // OK is code 200
    } else if (author != null) {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author, pageable), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(bookService.allBooks(pageable), HttpStatus.OK);
    }
}


// Get a single book by title
@ApiOperation(value = "Get a single book by its title")
@GetMapping("/Books/title/{title}")
public ResponseEntity<Book> getSingleBook(@PathVariable String title) {
    Book book = bookService.singleBook(title).orElseThrow(() -> new BookNotFoundException("Book not found with title: " + title));
    return new ResponseEntity<>(book, HttpStatus.OK);
}

// Add a new book
@ApiOperation(value = "Add a book with all associated information")
@PostMapping("/AddBooks")
public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
    Book savedBook = bookService.addBook(book);
    return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
}

//update a book
@ApiOperation(value = "Update a book with the given title")
@PutMapping("/Books/{title}")
public ResponseEntity<Book> updateBook(@PathVariable("title") String title, @Valid @RequestBody Book book) {
    Book updatedBook = bookService.updateBook(title, book).orElseThrow(() -> new BookNotFoundException("Book not found with title: " + title));
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
}

// Delete a book
@ApiOperation(value = "Delete a book by its title")
@DeleteMapping("/Books/{title}")
public ResponseEntity<Void> deleteBookByTitle(@PathVariable String title) {
    Book bookToDelete = bookService.singleBook(title).orElseThrow(() -> new BookNotFoundException("Book not found with title: " + title));
    bookService.deleteBookByTitle(title);
    return ResponseEntity.noContent().build();
}
}
