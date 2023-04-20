package online.javaclass.bookstore.service.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemDto {
    private Long id;
    private Long orderId;
    @NotNull(message = "{error.default_client}")
    private BookDto book;
    @NotNull(message = "{error.invalid_quantity}")
    private Integer quantity;
    @NotNull(message = "{error.invalid_price}")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto itemDto = (OrderItemDto) o;
        return Objects.equals(id, itemDto.id) && Objects.equals(orderId, itemDto.orderId) && Objects.equals(book, itemDto.book) && Objects.equals(quantity, itemDto.quantity) && Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, book, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", bookDto=" + book +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}