package org.example.restapp.service;

import org.example.restapp.model.Book;

import java.util.List;

// CRUD операции над клиентом
public interface BookService {

    // Создает новую книгу
    void create(Book book, String isbn);

    // Возвращает список всех имеющихся книг
    List<Book> readALL();

    // Возвращает книгу по isbn
    Book read(String isbn);

    // Обновляет книгу с заданныи isbn
    void update(String isbn, String newTitle);

    // Удаляет книгу с заданным isbn
    boolean delete(String isbn);


}
