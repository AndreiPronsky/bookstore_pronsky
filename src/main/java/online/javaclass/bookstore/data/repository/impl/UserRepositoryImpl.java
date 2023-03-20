package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        return userDao.getAll(limit, offset);
    }

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        if (userDao.getByEmail(email) == null) {
            return null;
        } else {
            return userDao.getByEmail(email);
        }
    }

    @Override
    public List<User> getByLastName(String lastname, int limit, int offset) {
        return userDao.getByLastName(lastname, limit, offset);
    }

    @Override
    public Long count() {
        return userDao.count();
    }
}
