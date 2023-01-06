package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<Long, User> {

    User getByEmail(String email);

    List<User> getByLastName(String lastname, int limit, int offset);

    Long count();
}
