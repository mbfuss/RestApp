package org.example.restapp.controller;

import org.example.restapp.model.Book;
import org.example.restapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// В данном классе реализована логика обработки запросов
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Методы контроллера для обработки CRUD операций
    // ResponseEntity — специальный класс для возврата ответов
    // С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код
    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody Book book){
        // Создание новой книги
        bookService.create(book, book.getIsbn());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Book>> read(){
        // Получение всех книг
        final List<Book> bookList = bookService.readALL();

        return bookList != null && !bookList.isEmpty()
                ? new ResponseEntity<>(bookList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Book> read(@PathVariable(name = "isbn") String isbn) {
        // Получение книги по ее ISBN
        final Book book = bookService.read(isbn);

        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/")
    public ResponseEntity<?> update(@RequestBody Map<String, String> request){
        // Обновление информации о книге
        String isbn = request.get("isbn");
        String newTitle = request.get("title");
        bookService.update(isbn, newTitle);

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/isbn/{isbn}")
    public ResponseEntity<?> delete (@PathVariable(name = "isbn") String isbn){
        // Удаление книги по ее ISBN
        final boolean deleted = bookService.delete(isbn);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
