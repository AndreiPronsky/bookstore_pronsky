package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByLastName(String lastname);

    @Query("FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> login(String email, String password);
}
