package org.example.restapp.repository;

import org.example.restapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository  extends JpaRepository<Book, String> {

    Book findByIsbn(String isbn);

    @Modifying
    @Query(value = "DELETE FROM Book b WHERE b.isbn = :isbn")
    void deleteByIsbn(@Param("isbn") String isbn);
}
