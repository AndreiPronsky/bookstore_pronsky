package online.javaclass.bookstore.service.mapper;

import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.OrderDto;
import online.javaclass.bookstore.service.dto.OrderItemDto;
import online.javaclass.bookstore.service.dto.UserDto;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    BookDto toDto(Book entity);

    Book toEntity(BookDto dto);

    OrderDto toDto(Order entity);

    Order toEntity(OrderDto dto);

    OrderItemDto toDto(OrderItem entity);

    OrderItem toEntity(OrderItemDto dto);

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}