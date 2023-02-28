package online.javaclass.bookstore.data;

import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityDtoMapperData {

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(User.Role.valueOf(userDto.getRole().toString()));
        user.setRating(userDto.getRating());
        return user;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(UserDto.Role.valueOf(user.getRole().toString()));
        userDto.setRating(user.getRating());
        return userDto;
    }

    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderStatus(Order.OrderStatus.values()[orderDto.getOrderStatus().ordinal()]);
        order.setPaymentMethod(Order.PaymentMethod.values()[orderDto.getPaymentMethod().ordinal()]);
        order.setPaymentStatus(Order.PaymentStatus.values()[orderDto.getPaymentStatus().ordinal()]);
        order.setDeliveryType(Order.DeliveryType.values()[orderDto.getDeliveryType().ordinal()]);
        order.setItems(getOrderItemList(orderDto));
        order.setCost(orderDto.getCost());
        return order;
    }

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderStatus(OrderDto.OrderStatus.values()[order.getOrderStatus().ordinal()]);
        orderDto.setPaymentMethod(OrderDto.PaymentMethod.values()[order.getPaymentMethod().ordinal()]);
        orderDto.setPaymentStatus(OrderDto.PaymentStatus.values()[order.getPaymentStatus().ordinal()]);
        orderDto.setDeliveryType(OrderDto.DeliveryType.values()[order.getDeliveryType().ordinal()]);
        orderDto.setItems(getOrderItemDtoList(order));
        orderDto.setCost(order.getCost());
        return orderDto;
    }

    public Book toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setGenre(Book.Genre.valueOf(bookDto.getGenre().toString()));
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        book.setRating(bookDto.getRating());
        return book;
    }

    public BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setGenre(BookDto.Genre.valueOf(book.getGenre().toString()));
        bookDto.setCover(BookDto.Cover.valueOf(book.getCover().toString()));
        bookDto.setPages(book.getPages());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
    }

    public OrderItemDto toDto(OrderItem item) {
        if (item == null) {
            return null;
        }
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setId(item.getId());
        itemDto.setOrderId(item.getOrderId());
        itemDto.setBookId(item.getBookId());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setPrice(item.getPrice());
        return itemDto;
    }

    public OrderItem toEntity(OrderItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }
        OrderItem item = new OrderItem();
        item.setId(itemDto.getId());
        item.setOrderId(itemDto.getOrderId());
        item.setBookId(itemDto.getBookId());
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());
        return item;
    }

    private List<OrderItem> getOrderItemList(OrderDto orderDto) {
        return orderDto.getItems().stream()
                .map(this::toEntity)
                .toList();
    }

    private List<OrderItemDto> getOrderItemDtoList(Order order) {
        return order.getItems().stream()
                .map(this::toDto)
                .toList();
    }
}
