package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<Long, User> {
    @Override
    User findById(Long id);

    @Override
    List<User> findAll();

    @Override
    User create(User entity);

    @Override
    User update(User entity);

    @Override
    boolean deleteById(Long id);

    User findByEmail(String email);

    List<User> findByLastName(String lastName);

    Long count();
}
