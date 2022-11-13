package online.javaclass.bookstore;

import online.javaclass.bookstore.controller.BookController;
import online.javaclass.bookstore.controller.impl.BookControllerImpl;
import online.javaclass.bookstore.data.connection.ConnectionProperties;
import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dao.impl.BookDaoImpl;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.impl.BookServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class BookServerDemo {

    public static void main(String[] args) {
        String pathToProps = "src/main/resources/connectionConfig.properties";
        DataBaseManager dataBaseManager = getDataBaseManager(pathToProps);
        BookDao bookDao = new BookDaoImpl(dataBaseManager);
        BookService bookService = new BookServiceImpl(bookDao);
        BookController bookController = new BookControllerImpl(bookService);
        try (ServerSocket server = new ServerSocket(8181)){
            while (true) {
                try (Socket socket = server.accept()) {
                    InputStream input = socket.getInputStream();
                    String content = getRequestContent(input);
                    System.out.println(content);
                    PrintStream output = new PrintStream(socket.getOutputStream());
                    bookController.process(content, output);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static DataBaseManager getDataBaseManager(String pathToProperties) {
        Map<String, String> connectionProps = ConnectionProperties.getConnectionProperties(pathToProperties);
        String url = connectionProps.get("URL");
        String user = connectionProps.get("USER");
        String password = connectionProps.get("PASSWORD");
        return new DataBaseManager(url, user, password);
    }

    private static String getRequestContent(InputStream input) throws IOException {
        int read;
        char previous = 0;
        StringBuilder requestContent = new StringBuilder();
        while ((read = input.read()) != -1) {
            char current = (char)read;
            requestContent.append(current);
            if (current == '\r' && previous == '\n') {
                break;
            }
            previous = current;
        }
        return String.valueOf(requestContent);
    }
}
