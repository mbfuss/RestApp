package org.example.restapp.service;

import org.example.restapp.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Бизнес-логика работы с книгами
@Service
public class BookServiceImpl implements BookService{
    // Хранилище книг
    private static final Map<String, Book> BOOK_MAP_KEEP = new HashMap<>();

    @Override
    public void create(Book book, String isbn) {
        // Устанавливаем ISBN книги
        book.setIsbn(isbn);
        // Добавляем книгу в хранилище
        BOOK_MAP_KEEP.put(isbn, book);
    }

    @Override
    public List<Book> readALL() {
        // Возвращаем список книг, скопировав значения из хранилища
        return new ArrayList<>(BOOK_MAP_KEEP.values());
    }

    @Override
    public Book read(String isbn) {
        // Возвращаем книгу по ее ISBN
        return BOOK_MAP_KEEP.get(isbn);
    }

    @Override
    public void update(String isbn, String newTitle) {
        // Обновляем заголовок книги по ее ISBN
        Book book = BOOK_MAP_KEEP.get(isbn);
        if (book != null) {
            book.setTitle(newTitle);
        }
    }

    @Override
    public boolean delete(String isbn) {
        // Удаляем книгу из хранилища по ее ISBN
        return BOOK_MAP_KEEP.remove(isbn) != null;
    }
}
