package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    void update(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    List<User> findUsersByLastName(String lastname);

    List<User> findAll();

    boolean deleteById(Long id);
}
