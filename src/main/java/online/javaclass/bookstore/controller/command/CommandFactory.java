package online.javaclass.bookstore.controller.command;

import online.javaclass.bookstore.controller.command.impl.*;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dao.impl.UserDaoImpl;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.repository.UserRepository;
import online.javaclass.bookstore.data.repository.impl.UserRepositoryImpl;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.data.repository.impl.BookRepositoryImpl;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.impl.BookServiceImpl;
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
        EntityDtoMapperData dataMapper = new EntityDtoMapperData(userDao);
        EntityDtoMapperService serviceMapper = new EntityDtoMapperService();
        BookRepository bookRepository = new BookRepositoryImpl(bookDao, dataMapper);
        BookService bookService = new BookServiceImpl(bookRepository, serviceMapper);
        UserRepository userRepository = new UserRepositoryImpl(userDao, dataMapper);
        UserService userService = new UserServiceImpl(userRepository, serviceMapper);

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
    }

    public Command getCommand(String command) {
        return map.get(command);
    }
}
