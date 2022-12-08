package online.javaclass.bookstore.data.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
}
