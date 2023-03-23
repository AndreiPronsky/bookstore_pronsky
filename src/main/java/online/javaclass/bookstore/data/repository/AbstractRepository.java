package online.javaclass.bookstore.data.repository;

import java.util.List;

public interface AbstractRepository<K, T> {
    T findById(K id);

    List<T> findAll(int limit, int offset);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    Long count();
}
