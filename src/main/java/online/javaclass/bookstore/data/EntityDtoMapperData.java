package online.javaclass.bookstore.data;

import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.dto.BookDto;

public class EntityDtoMapperData {
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
        book.setGenre(Book.Genre.valueOf(bookDto.getGenre().toString()));
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
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
        bookDto.setGenre(BookDto.Genre.valueOf(book.getGenre().toString()));
        bookDto.setCover(BookDto.Cover.valueOf(book.getCover().toString()));
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

    public OrderItemDto toDto(OrderItem item) {
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setId(item.getId());
        itemDto.setOrderId(item.getOrderId());
        itemDto.setBookId(item.getBookId());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setPrice(item.getPrice());
        return itemDto;
    }

    public OrderItem toEntity(OrderItemDto itemDto) {
        OrderItem item = new OrderItem();
        item.setId(itemDto.getId());
        item.setOrderId(itemDto.getOrderId());
        item.setBookId(itemDto.getBookId());
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());
        return item;
    }
}
