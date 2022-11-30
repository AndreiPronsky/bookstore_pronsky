package online.javaclass.bookstore.service;

import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.UserDto;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.User;

public class EntityDtoMapperService {
    public User toEntity(UserDto userDto) {
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

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderStatus(Order.OrderStatus.valueOf(orderDto.getOrderStatus().toString()));
        order.setPaymentMethod(Order.PaymentMethod.valueOf(orderDto.getPaymentMethod().toString()));
        order.setPaymentStatus(Order.PaymentStatus.valueOf(orderDto.getPaymentStatus().toString()));
        order.setDeliveryType(Order.DeliveryType.valueOf(orderDto.getDeliveryType().toString()));
        order.setCost(orderDto.getCost());
        return order;
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

    public UserDto toDto(User user) {
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
}
