package online.javaclass.bookstore;

import online.javaclass.bookstore.controller.Controller;
import online.javaclass.bookstore.controller.impl.BookControllerImpl;
import online.javaclass.bookstore.controller.impl.UserControllerImpl;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dao.UserDao;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.data.dao.impl.UserDaoImpl;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.impl.BookServiceImpl;
import online.javaclass.bookstore.service.impl.UserServiceImpl;

import java.util.Scanner;

public class Server {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/bookstore_pronsky";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        DataBaseManager dataBaseManager = new DataBaseManager(URL, USER, PASSWORD);
        UserDao userDao = new UserDaoImpl(dataBaseManager);
        UserService userService = new UserServiceImpl(userDao);
            Controller userController = new UserControllerImpl(userService);
        BookDao bookDao = new BookDaoImpl(dataBaseManager);
        BookService bookService = new BookServiceImpl(bookDao);
            Controller bookController = new BookControllerImpl(bookService);

        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                USER HELP :\s
                get {id} / {all} / {lastname} / {email}\s
                create {firstName, lastName, email, password, role, rating} DELIMITER ', '!\s
                update {id, firstName, lastName, email, password, role, rating} DELIMITER ', '!\s
                delete {id}\s
                exit""");

        System.out.println("""
                BOOK HELP :\s
                get {id} / {all} / {author} / {isbn}\s
                create {title, author, isbn, cover, pages, price, rating} DELIMITER ', '!\s
                update {id, title, author, isbn, cover, pages, price, rating} DELIMITER ', '!\s
                delete {id}\s
                exit""");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            bookController.process(input, System.out);
            userController.process(input, System.out);
        }
    }
}
