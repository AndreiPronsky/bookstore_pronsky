package online.javaclass.bookstore.data.repository;

import online.javaclass.bookstore.data.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserId(Pageable pageable, Long userId);
}
