package online.javaclass.bookstore.service.dto;

import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemDto {
    private Long id;
    private Order order;
    private Book book;
    private Integer quantity;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
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
        return Objects.equals(id, itemDto.id) && Objects.equals(order, itemDto.order) && Objects.equals(book, itemDto.book) && Objects.equals(quantity, itemDto.quantity) && Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, book, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", order=" + order +
                ", book=" + book +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}