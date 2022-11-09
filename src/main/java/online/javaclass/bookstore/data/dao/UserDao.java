package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserDao {
    User create(User user);

    User update(User user);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findByLastName(String lastname);

    List<User> findAll();

    boolean deleteById(Long id);

    Long count();
}
