package online.javaclass.bookstore.service.mapper;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.*;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.dto.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityDtoMapper {
    private final OrderRepository orderRepo;
    private final UserRepository userRepository;

    @LogInvocation
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
        if (userDto.getPreferencesDto() != null) {
            user.setPreferences(this.toEntity(userDto.getPreferencesDto()));
        }
        return user;
    }

    @LogInvocation
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
        userDto.setPreferencesDto(this.toDto(user.getPreferences()));
        return userDto;
    }

    @LogInvocation
    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }
        order.setUser(toEntity(orderDto.getUser()));
        order.setOrderStatus(Order.OrderStatus.valueOf(orderDto.getOrderStatus().toString()));
        order.setPaymentMethod(Order.PaymentMethod.valueOf(orderDto.getPaymentMethod().toString()));
        order.setPaymentStatus(Order.PaymentStatus.valueOf(orderDto.getPaymentStatus().toString()));
        order.setDeliveryType(Order.DeliveryType.valueOf(orderDto.getDeliveryType().toString()));
        order.setItems(orderDto.getItems().stream().map(this::toEntity).toList());
        order.setCost(orderDto.getCost());
        return order;
    }

    @LogInvocation
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
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }
        orderDto.setItems(order.getItems().stream().map(this::toDto).toList());
        orderDto.setCost(order.getCost());
        return orderDto;
    }

    @LogInvocation
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
        book.setAvailable(bookDto.isAvailable());
        return book;
    }

    @LogInvocation
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
        bookDto.setAvailable(book.isAvailable());
        return bookDto;
    }

    @LogInvocation
    public OrderItemDto toDto(OrderItem item) {
        if (item == null) {
            return null;
        }
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setOrderId(item.getOrder().getId());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setPrice(item.getPrice());
        itemDto.setBook(this.toDto(item.getBook()));
        itemDto.setId(item.getId());
        return itemDto;
    }

    @LogInvocation
    public OrderItem toEntity(OrderItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(this.toEntity(itemDto.getBook()));
        orderItem.setPrice(itemDto.getPrice());
        orderItem.setId(itemDto.getId());
        if (itemDto.getOrderId() != null) {
            Optional<Order> order = orderRepo.findById(itemDto.getOrderId());
            orderItem.setOrder(order.orElseThrow(RuntimeException::new));
        }
        orderItem.setQuantity(itemDto.getQuantity());
        return orderItem;
    }

    @LogInvocation
    public UserPreferences toEntity(UserPreferencesDto preferencesDto) {
        UserPreferences preferences = new UserPreferences();
        preferences.setId(preferencesDto.getId());
        preferences.setUser(userRepository.findById(preferencesDto.getUserId()).orElseThrow(RuntimeException::new));
        preferences.setPreferredLocale(preferencesDto.getPreferredLocale());
        return preferences;
    }

    @LogInvocation
    public UserPreferencesDto toDto(UserPreferences preferences) {
        if (preferences == null) {
            return null;
        }
        UserPreferencesDto preferencesDto = new UserPreferencesDto();
        preferencesDto.setId(preferences.getId());
        preferencesDto.setUserId(preferences.getUser().getId());
        preferencesDto.setPreferredLocale(preferences.getPreferredLocale());

        return preferencesDto;
    }
}
