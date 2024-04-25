package org.example.restapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.restapp.model.Book;
import org.example.restapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Бизнес-логика работы с книгами
@Service
public class BookServiceImpl implements BookService{
    // Хранилище книг
    @Autowired
    BookRepository bookRepository;

    @Override
    public void create(Book book) {
        // Добавляем книгу в хранилище
        bookRepository.save(book);
    }

    @Override
    public List<Book> readALL() {
        // Возвращаем список книг, скопировав значения из хранилища
        return bookRepository.findAll();
    }

    @Override
    public Book read(String isbn) {
        // Возвращаем книгу по ее ISBN
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public void update(String isbn, String newTitle) {
        // Обновляем заголовок книги по ее ISBN
        Book updatedBook = bookRepository.findByIsbn(isbn);
        if (updatedBook != null) {
            updatedBook.setTitle(newTitle);
            bookRepository.save(updatedBook);
        }
    }

    @Override
    public boolean delete(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book != null) {
            bookRepository.delete(book);
        } else {
            throw new EntityNotFoundException("Book with ISBN " + isbn + " not found");
        }
        return false;
    }


}
