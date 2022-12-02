package online.javaclass.bookstore.data;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dto.OrderDto;
import online.javaclass.bookstore.data.dto.OrderItemDto;
import online.javaclass.bookstore.data.dto.UserDto;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Order;
import online.javaclass.bookstore.data.entities.OrderItem;
import online.javaclass.bookstore.data.entities.User;
import online.javaclass.bookstore.data.dto.BookDto;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class EntityDtoMapperData {
    private final UserDao userDao;

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
        userDto.setRole(UserDto.Role.values()[(user.getRole().ordinal())]);
        userDto.setRating(user.getRating());
        return userDto;
    }

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUser(toEntity(userDao.findById(orderDto.getUserId())));
        order.setOrderStatus(Order.OrderStatus.values()[orderDto.getOrderStatus().ordinal()]);
        order.setPaymentMethod(Order.PaymentMethod.values()[orderDto.getPaymentMethod().ordinal()]);
        order.setPaymentStatus(Order.PaymentStatus.values()[orderDto.getPaymentStatus().ordinal()]);
        order.setDeliveryType(Order.DeliveryType.values()[orderDto.getDeliveryType().ordinal()]);
        order.setItems(getOrderItemList(orderDto));
        order.setCost(calculateCost(orderDto));
        return order;
    }

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderStatus(OrderDto.OrderStatus.values()[order.getOrderStatus().ordinal()]);
        orderDto.setPaymentMethod(OrderDto.PaymentMethod.values()[order.getOrderStatus().ordinal()]);
        orderDto.setPaymentStatus(OrderDto.PaymentStatus.values()[order.getPaymentStatus().ordinal()]);
        orderDto.setDeliveryType(OrderDto.DeliveryType.values()[order.getDeliveryType().ordinal()]);
        orderDto.setItems(getOrderItemDtoList(order));
        orderDto.setCost(order.getCost());
        return orderDto;
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setGenre(Book.Genre.values()[bookDto.getGenre().ordinal()]);
        book.setCover(Book.Cover.values()[bookDto.getCover().ordinal()]);
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
        bookDto.setGenre(BookDto.Genre.values()[book.getGenre().ordinal()]);
        bookDto.setCover(BookDto.Cover.values()[book.getCover().ordinal()]);
        bookDto.setPages(book.getPages());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
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

    private BigDecimal calculateCost(OrderDto orderDto) {
        List<OrderItemDto> itemDtos = orderDto.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : itemDtos) {
            BigDecimal itemCost = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
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
