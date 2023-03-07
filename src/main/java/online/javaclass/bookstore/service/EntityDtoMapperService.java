package online.javaclass.bookstore.service;

import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapperService {
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
        user.setRole(User.Role.values()[(userDto.getRole().ordinal())]);
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
        order.setUser(toEntity(orderDto.getUser()));
        order.setOrderStatus(Order.OrderStatus.valueOf(orderDto.getOrderStatus().toString()));
        order.setPaymentMethod(Order.PaymentMethod.valueOf(orderDto.getPaymentMethod().toString()));
        order.setPaymentStatus(Order.PaymentStatus.valueOf(orderDto.getPaymentStatus().toString()));
        order.setDeliveryType(Order.DeliveryType.valueOf(orderDto.getDeliveryType().toString()));
        order.setItems(orderDto.getItems().stream().map(this::toEntity).toList());
        order.setCost(orderDto.getCost());
        return order;
    }

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        UserDto userDto = toDto(order.getUser());
        orderDto.setUser(userDto);
        orderDto.setOrderStatus(OrderDto.OrderStatus.valueOf(order.getOrderStatus().toString()));
        orderDto.setPaymentMethod(OrderDto.PaymentMethod.valueOf(order.getPaymentMethod().toString()));
        orderDto.setPaymentStatus(OrderDto.PaymentStatus.valueOf(order.getPaymentStatus().toString()));
        orderDto.setDeliveryType(OrderDto.DeliveryType.valueOf(order.getDeliveryType().toString()));
        orderDto.setItems(order.getItems().stream().map(this::toDto).toList());
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
        book.setGenre(Book.Genre.values()[(bookDto.getGenre().ordinal())]);
        book.setCover(Book.Cover.values()[(bookDto.getCover().ordinal())]);
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
        itemDto.setOrderId(item.getOrderId());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setPrice(item.getPrice());
        itemDto.setBookId(item.getBookId());
        itemDto.setId(item.getId());
        return itemDto;
    }

    public OrderItem toEntity(OrderItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setBookId(itemDto.getBookId());
        orderItem.setPrice(itemDto.getPrice());
        orderItem.setId(itemDto.getId());
        orderItem.setOrderId(itemDto.getOrderId());
        orderItem.setQuantity(itemDto.getQuantity());
        return orderItem;
    }
}
