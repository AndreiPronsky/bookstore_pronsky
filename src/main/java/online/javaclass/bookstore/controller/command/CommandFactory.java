package online.javaclass.bookstore.controller.command;

import online.javaclass.bookstore.controller.command.impl.*;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.data.dao.impl.UserDaoImpl;
import online.javaclass.bookstore.service.BookService;
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
        BookService bookService = new BookServiceImpl(new BookDaoImpl(manager));
        UserService userService = new UserServiceImpl(new UserDaoImpl(manager));
        map = new HashMap<>();
        map.put("book", new BookCommand(bookService));
        map.put("books", new BooksCommand(bookService));
        map.put("user", new UserCommand(userService));
        map.put("users", new UsersCommand(userService));
        map.put("error", new ErrorCommand());
    }

    public Command getCommand(String command) {
        return map.get(command);
    }
}
