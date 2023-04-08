package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthor(String author);
    List<Book> findByAuthorLikeOrTitleLike(String input);
}
