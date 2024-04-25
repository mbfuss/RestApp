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


    //Аннотация @PostMapping(value = "/") указывает на то, что этот метод будет обрабатывать HTTP POST запросы на указанном маршруте "/".
    //
    //Параметр @RequestBody Book book указывает на то, что метод ожидает получить данные в формате JSON, которые будут преобразованы в объект класса Book.
    //
    //Далее вызывается метод create() сервисного слоя bookService, который отвечает за сохранение книги в базе данных. В данном случае передается сам объект книги и ее ISBN.
    //
    //Наконец, возвращается ResponseEntity<?> с кодом статуса HttpStatus.CREATED, что означает успешное создание новой книги.
    //
    //Таким образом, данный метод контроллера позволяет добавить новую книгу в базу данных через HTTP POST запрос, принимая данные в формате JSON и сохраняя их с использованием сервисного слоя.
    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody Book book){
        // Создание новой книги
        bookService.create(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //
    //1. @GetMapping(value = "/all"): Аннотация @GetMapping указывает, что метод будет обрабатывать HTTP GET-запросы на заданный эндпоинт "/all".
    //
    //2. public ResponseEntity<List<Book>> read(): Описание метода read(), который возвращает объект ResponseEntity, содержащий список книг.
    //
    //3. final List<Book> bookList = bookService.readALL();: Создание переменной bookList и вызов метода readALL() из компонента bookService для получения списка книг.
    //
    //4. return bookList != null && !bookList.isEmpty() ? new ResponseEntity<>(bookList, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);:
    // Возврат объекта ResponseEntity в зависимости от условия. Если список книг не пустой и не равен null, возвращается список книг с HTTP статусом OK (200). В противном случае, возвращается HTTP статус NOT_FOUND (404).
    //
    //Этот метод позволяет получить список всех книг, когда отправляется GET-запрос на эндпоинт "/all". Если список книг не найден или пуст, будет возвращен соответствующий HTTP статус в ответе.
    //
    //Для корректной работы данного метода необходимо убедиться, что компонент bookService правильно настроен и содержит метод readALL(), который возвращает список книг.
    @GetMapping(value = "/all")
    public ResponseEntity<List<Book>> read(){
        // Получение всех книг
        final List<Book> bookList = bookService.readALL();

        return bookList != null && !bookList.isEmpty()
                ? new ResponseEntity<>(bookList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 1. `@GetMapping(value = "/isbn/{isbn}")`:
    //   - Аннотация `@GetMapping` указывает на то, что метод обрабатывает HTTP GET запросы.
    //   - Значение `"/isbn/{isbn}"` указывает на путь запроса, где `{isbn}` является переменной пути, которая будет передаваться в метод контроллера.
    //
    //2. `public ResponseEntity<Book> read(@PathVariable(name = "isbn") String isbn)`:
    //   - Объявление метода `read()`, который возвращает объект `ResponseEntity<Book>`.
    //   - Аннотация `@PathVariable` используется для извлечения значения переменной пути `isbn`, которая передается в метод.
    //   - В данном случае, значение переменной пути `isbn` будет передано методу в качестве строки.
    //
    //3. `final Book book = bookService.read(isbn);`:
    //   - В этой строке вызывается метод `read()` сервиса `bookService`, который возвращает книгу по указанному ISBN.
    //   - Полученная книга сохраняется в переменной `book`.
    //
    //4. Возвращение `ResponseEntity`:
    //   - В следующей строке используется тернарный оператор для проверки, была ли найдена книга по указанному ISBN.
    //   - Если книга была найдена (`book != null`), то возвращается `ResponseEntity` с кодом статуса `HttpStatus.OK` и найденной книгой в теле ответа.
    //   - Если книга не была найдена (`book == null`), то возвращается `ResponseEntity` с кодом статуса `HttpStatus.NOT_FOUND`.
    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Book> read(@PathVariable(name = "isbn") String isbn) {
        // Получение книги по ее ISBN
        final Book book = bookService.read(isbn);

        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //1. @PutMapping(value = "/"):
    //   - Аннотация @PutMapping указывает на то, что метод обрабатывает HTTP PUT запросы.
    //   - Значение "/" указывает на корневой путь запроса.
    //
    //2. public ResponseEntity<?> update(@RequestBody Map<String, String> request):
    //   - Объявление метода update(), который принимает объект Map<String, String> в теле запроса и возвращает объект ResponseEntity<?>.
    //   - Аннотация @RequestBody используется для преобразования тела запроса в указанный тип данных (в данном случае - Map<String, String>).
    //
    //3. String isbn = request.get("isbn");
    //   String newTitle = request.get("title");
    //   bookService.update(isbn, newTitle);:
    //   - В этих строках извлекается значение isbn и newTitle из тела запроса (переданного в виде Map<String, String>).
    //   - Затем вызывается метод update() сервиса bookService, который обновляет информацию о книге по переданному ISBN и новому заголовку.
    //
    //4. Возвращение ResponseEntity:
    //   - Далее возвращается ResponseEntity с кодом статуса HttpStatus.NOT_MODIFIED.
    //   - Этот статус сообщает, что запрошенный ресурс не был изменен, так как возвращается данный статус после успешного обновления информации о книге.
    @PutMapping(value = "/")
    public ResponseEntity<?> update(@RequestBody Map<String, String> request){
        // Обновление информации о книге
        String isbn = request.get("isbn");
        String newTitle = request.get("title");
        bookService.update(isbn, newTitle);

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

//1. @DeleteMapping(value = "/isbn/{isbn}"):
//   - Аннотация @DeleteMapping указывает на то, что данный метод обрабатывает HTTP DELETE запросы.
//   - Значение "/isbn/{isbn}" указывает на путь, где {isbn} является переменной пути (параметром), который будет передаваться в качестве аргумента в метод.
//
//2. public ResponseEntity<?> delete (@PathVariable(name = "isbn") String isbn):
//   - Объявление метода delete(), который принимает ISBN книги в качестве параметра isbn из пути запроса.
//   - Аннотация @PathVariable используется для передачи значения переменной пути (в данном случае - isbn) как аргумента методу.
//
//3. final boolean deleted = bookService.delete(isbn);:
//   - В этой строке вызывается метод delete() сервиса bookService, который пытается удалить книгу по переданному ISBN.
//   - Результат операции удаления (true или false) сохраняется в переменной deleted.
//
//4. Возвращение ResponseEntity:
//   - Далее возвращается ResponseEntity в зависимости от результата операции удаления:
//     - Если книга была успешно удалена, возвращается HttpStatus.OK.
//     - Если книга не была удалена (например, книга с указанным ISBN не найдена), возвращается HttpStatus.NOT_MODIFIED.
    @DeleteMapping(value = "/isbn/{isbn}")
    public ResponseEntity<?> delete (@PathVariable(name = "isbn") String isbn){
        // Удаление книги по ее ISBN
        final boolean deleted = bookService.delete(isbn);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    //ResponseEntity в Spring Framework представляет собой класс, который предназначен для возврата ответа из контроллера обработчику запросов. Он содержит не только тело ответа, но также заголовки и статус HTTP ответа.
    //
    //Ниже представлено описание основных составляющих ResponseEntity:
    //
    //1. body - тело ответа. Это может быть любой объект, который будет возвращаться в ответе на запрос.
    //
    //2. headers - HTTP заголовки. Вы можете добавить кастомные заголовки к ответу.
    //
    //3. status - статус HTTP ответа. Это может быть любой статус HTTP, такой как HttpStatus.OK, HttpStatus.NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR, и т.д.
    //
    //ResponseEntity позволяет вам явно управлять созданием ответов в ваших контроллерах, включая тело, заголовки и статус ответа. Он дает вам больше гибкости и контроля над ответами, чем использование базового типа возврата.
    //
    //Метод delete() в вашем контроллере возвращает ResponseEntity, чтобы сообщить клиенту о статусе операции удаления книги. В случае успешного удаления, возвращается HttpStatus.OK, а в случае неудачи - HttpStatus.NOT_MODIFIED.
    //
    //По умолчанию возвращаемый Content-Type в ResponseEntity - application/json, но вы можете управлять его и изменять при необходимости.

}
