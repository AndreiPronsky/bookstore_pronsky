package online.javaclass.bookstore.service;

import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.UserDto;

public class EntityDtoMapperService {
    public User toEntity(UserDto userDto) {
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
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(UserDto.Role.values()[(user.getRole().ordinal()) - 1]);
        userDto.setRating(user.getRating());
        return userDto;
    }

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUser(toEntity(orderDto.getUser()));
        order.setOrderStatus(Order.OrderStatus.valueOf(orderDto.getOrderStatus().toString()));
        order.setPaymentMethod(Order.PaymentMethod.valueOf(orderDto.getPaymentMethod().toString()));
        order.setPaymentStatus(Order.PaymentStatus.valueOf(orderDto.getPaymentStatus().toString()));
        order.setDeliveryType(Order.DeliveryType.valueOf(orderDto.getDeliveryType().toString()));
        order.setItems(orderDto.getItems());
        order.setCost(orderDto.getCost());
        return order;
    }

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(toDto(order.getUser()));
        orderDto.setOrderStatus(OrderDto.OrderStatus.valueOf(order.getOrderStatus().toString()));
        orderDto.setPaymentMethod(OrderDto.PaymentMethod.valueOf(order.getPaymentMethod().toString()));
        orderDto.setPaymentStatus(OrderDto.PaymentStatus.valueOf(order.getPaymentStatus().toString()));
        orderDto.setDeliveryType(OrderDto.DeliveryType.valueOf(order.getDeliveryType().toString()));
        orderDto.setItems(order.getItems());
        orderDto.setCost(order.getCost());
        return orderDto;
    }


    public Book toEntity(BookDto bookDto) {
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
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setGenre(BookDto.Genre.values()[(book.getGenre().ordinal())]);
        bookDto.setCover(BookDto.Cover.values()[(book.getCover().ordinal())]);
        bookDto.setPages(book.getPages());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
    }


}
