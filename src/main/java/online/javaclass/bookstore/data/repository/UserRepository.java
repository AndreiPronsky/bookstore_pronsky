package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<Long, User> {

    User findByEmail(String email);

    List<User> findByLastName(String lastname, int limit, int offset);
}
