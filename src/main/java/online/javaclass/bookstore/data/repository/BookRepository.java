package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByAuthor(Pageable pageable, String author);

    @Query("FROM Book b WHERE b.title LIKE %:input% OR b.author LIKE %:input%")
    List<Book> search(@Param("input") String input);

}
