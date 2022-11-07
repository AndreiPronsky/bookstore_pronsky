package online.javaclass.bookstore.data.dao.daoImpl;

import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDaoImpl implements BookDao {

    public static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, cover, pages, price, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_BOOK = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books WHERE book_id = ?";
    public static final String FIND_BOOK_BY_ID = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books WHERE book_id = ?";
    public static final String FIND_BOOK_BY_ISBN = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books WHERE isbn = ?";
    public static final String FIND_ALL = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books";
    public static final String FIND_BOOKS_BY_AUTHOR = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books WHERE author = ?";
    public static final String DELETE_BY_ID = "DELETE FROM books WHERE book_id = ?";

    private final DataBaseManager dataBaseManager;

    public BookDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }


    public void create(Book book) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setString(4, book.getCover());
            statement.setInt(5, book.getPages());
            statement.setBigDecimal(6, book.getPrice());
            statement.setBigDecimal(7, book.getRating());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                book.setId(result.getLong("book_id"));
                System.out.println("Created : " + findBookById(result.getLong("book_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Creation failed! " + e.getMessage());
        }
    }

    public void update(Book book) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE); Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter column label you want to modify: ");
            String column = scanner.next();
            System.out.print("Enter new value: ");
            String newValue = scanner.next();
            statement.setLong(1, book.getId());
            statement.executeQuery();
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                result.updateString(column, newValue);
                result.updateRow();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Update failed! " + e.getMessage());
        }
        System.out.println("Valid state : " + findBookById(book.getId()));
    }

    public Book findBookById(Long id) {
        Connection connection = dataBaseManager.getConnection();
        Book book = new Book();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("No such book found! " + e.getMessage());
        }
    }

    public Book findBookByIsbn(String isbn) {
        Connection connection = dataBaseManager.getConnection();
        Book book = new Book();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ISBN)) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("No such book found! " + e.getMessage());
        }
    }

    public List<Book> findAll() {
        Connection connection = dataBaseManager.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long id = result.getLong("book_id");
                Book book = findBookById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteById(Long id) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Deleted book with id : " + id);
                return true;
            } else {
                System.out.println("No such book found!");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete! " + e.getMessage());
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        Connection connection = dataBaseManager.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR)) {
            statement.setString(1, author);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long id = result.getLong("book_id");
                Book book = findBookById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setParameters(Book book, ResultSet result) {
        try {
            while (result.next()) {
                book.setId(result.getLong("book_id"));
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setIsbn(result.getString("isbn"));
                book.setCover(result.getString("cover"));
                book.setPages(result.getInt("pages"));
                book.setPrice(result.getBigDecimal("price"));
                book.setRating(result.getBigDecimal("rating"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
