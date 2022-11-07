package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    void update(User user);

    User findById(Long id);

    List<User> findAll();

    boolean deleteById(Long id);
}
