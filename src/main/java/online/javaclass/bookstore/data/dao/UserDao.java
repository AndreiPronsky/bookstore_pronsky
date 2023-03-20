package online.javaclass.bookstore.data.dao;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserDao extends AbstractDao<Long, User> {
    User getByEmail(String email);

    List<User> getByLastName(String lastname, int limit, int offset);

    Long count();
}
