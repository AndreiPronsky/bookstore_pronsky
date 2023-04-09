package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    @Query("FROM Book WHERE title LIKE :input OR author LIKE :input")
    List<Book> search(String input);
}
