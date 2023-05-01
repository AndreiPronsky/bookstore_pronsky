package online.javaclass.bookstore.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    @NotNull(message = "{error.default_client}")
    private BookDto book;
    @NotNull(message = "{error.invalid_quantity}")
    private Integer quantity;
    @NotNull(message = "{error.invalid_price}")
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto itemDto = (OrderItemDto) o;
        return Objects.equals(id, itemDto.id) && Objects.equals(book, itemDto.book) && Objects.equals(quantity, itemDto.quantity) && Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", bookDto=" + book +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}