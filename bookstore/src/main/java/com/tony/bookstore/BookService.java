package com.tony.bookstore;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> allBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // Add new methods for filtering and searching
    public Page<Book> searchBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Book> searchBooksByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }


    public Optional<Book> singleBook(String title) {
        return bookRepository.findBookByTitle(title);
    }

    public Book addBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistsException("A book with the title '" + book.getTitle() + "' and author '" + book.getAuthor() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(String title, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findBookByTitle(title);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setAuthor(updatedBook.getAuthor());
            book.setCountry(updatedBook.getCountry());
            book.setImageLink(updatedBook.getImageLink());
            book.setLanguage(updatedBook.getLanguage());
            book.setLink(updatedBook.getLink());
            book.setPages(updatedBook.getPages());
            book.setTitle(updatedBook.getTitle());
            book.setYear(updatedBook.getYear());
            bookRepository.save(book);
            return Optional.of(book);
        }

        return Optional.empty();
    }

    public void deleteBookByTitle(String title) {
        bookRepository.deleteBookByTitle(title);
    }
}
