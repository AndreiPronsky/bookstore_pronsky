package online.javaclass.bookstore.controller.command;

import online.javaclass.bookstore.controller.command.impl.*;
import online.javaclass.bookstore.controller.command.impl.bookCommands.*;
import online.javaclass.bookstore.controller.command.impl.orderCommands.*;
import online.javaclass.bookstore.controller.command.impl.userCommands.*;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dao.OrderDao;
import online.javaclass.bookstore.data.dao.OrderItemDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dao.impl.OrderDaoImpl;
import online.javaclass.bookstore.data.dao.impl.OrderItemDaoImpl;
import online.javaclass.bookstore.data.dao.impl.UserDaoImpl;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.repository.OrderRepository;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.data.repository.impl.OrderRepositoryImpl;
import online.javaclass.bookstore.data.repository.impl.UserRepositoryImpl;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.data.repository.impl.BookRepositoryImpl;
import online.javaclass.bookstore.service.*;
import online.javaclass.bookstore.service.impl.BookServiceImpl;
import online.javaclass.bookstore.service.impl.OrderServiceImpl;
import online.javaclass.bookstore.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    public static final CommandFactory INSTANCE = new CommandFactory();

    private final Map<String, Command> map;

    private CommandFactory() {
        DataBaseManager manager = DataBaseManager.INSTANCE;
        BookDao bookDao = new BookDaoImpl(manager);
        UserDao userDao = new UserDaoImpl(manager);
        OrderDao orderDao = new OrderDaoImpl(manager);
        OrderItemDao orderItemDao = new OrderItemDaoImpl(manager);
        EntityDtoMapperData dataMapper = new EntityDtoMapperData(userDao);
        EntityDtoMapperService serviceMapper = new EntityDtoMapperService();
        BookRepository bookRepository = new BookRepositoryImpl(bookDao, dataMapper);
        BookService bookService = new BookServiceImpl(bookRepository, serviceMapper);
        UserRepository userRepository = new UserRepositoryImpl(userDao, dataMapper);
        UserService userService = new UserServiceImpl(userRepository, serviceMapper);
        OrderRepository orderRepository = new OrderRepositoryImpl(orderDao, orderItemDao, dataMapper);
        OrderService orderService = new OrderServiceImpl(orderRepository, serviceMapper);
        map = new HashMap<>();
        map.put("book", new BookCommand(bookService));
        map.put("books", new BooksCommand(bookService));
        map.put("user", new UserCommand(userService));
        map.put("users", new UsersCommand(userService));
        map.put("error", new ErrorCommand());
        map.put("add_book_form", new AddBookFormCommand());
        map.put("add_book", new AddBookCommand(bookService));
        map.put("edit_book_form", new EditBookFormCommand(bookService));
        map.put("edit_book", new EditBookCommand(bookService));
        map.put("add_user_form", new AddUserFormCommand());
        map.put("add_user", new AddUserCommand(userService));
        map.put("edit_user_form", new EditUserFormCommand(userService));
        map.put("edit_user", new EditUserCommand(userService));
        map.put("home", new HomeCommand());
        map.put("login", new LoginCommand(userService));
        map.put("login_form", new LoginFormCommand());
        map.put("logout", new LogoutCommand());
        map.put("add_to_cart", new AddToCartCommand(bookService));
        map.put("cart", new CartCommand());
        map.put("confirm_order", new ConfirmOrderCommand(orderService));
        map.put("register_form", new RegisterFormCommand());
        map.put("register", new RegisterCommand(userService));
        map.put("change_lang", new ChangeLanguageCommand());
        map.put("search", new SearchCommand(bookService));
        map.put("corr_cart", new CorrectCartCommand());
        map.put("my_orders", new UserOrdersCommand(orderService, bookService));
        map.put("edit_order_form", new EditOrderFormCommand(orderService, bookService));
        map.put("edit_order", new EditOrderCommand(orderService));
        map.put("corr_order", new CorrectOrderCommand());
    }

    public Command getCommand(String command) {
        return map.get(command);
    }
}
