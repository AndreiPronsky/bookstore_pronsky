package online.javaclass.bookstore.data.dao.impl;

import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Cover;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    public static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, cover, pages, price, rating)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, isbn = ?," +
            " cover = ?, pages = ?, price = ?, rating = ? WHERE book_id = ?";
    public static final String FIND_BOOK_BY_ID = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE book_id = ?";
    public static final String FIND_BOOK_BY_ISBN = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE isbn = ?";
    public static final String FIND_ALL = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books";
    public static final String FIND_BOOKS_BY_AUTHOR = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE author = ?";
    public static final String DELETE_BY_ID = "DELETE FROM books WHERE book_id = ?";

    private final DataBaseManager dataBaseManager;

    static Logger logger = LogManager.getLogger();

    public BookDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }


    public Book create(Book book) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(book, statement);
            statement.executeUpdate();
            logger.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                book.setId(result.getLong("book_id"));
            }
            return findById(result.getLong("book_id"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "creation failed", e);
            throw new UnableToCreateException("Creation failed! " + e.getMessage());
        }
    }

    public Book update(Book book) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            prepareStatementForUpdate(book, statement);
            statement.executeUpdate();
            logger.debug("DB query completed");
            System.out.println("Valid state : " + findById(book.getId()));
            return findById(book.getId());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "update failed", e);
            throw new UnableToUpdateException("Update failed! " + e.getMessage());
        }
    }

    public Book findById(Long id) {
        Connection connection = dataBaseManager.getConnection();
        Book book = new Book();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            logger.debug("DB query completed");
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "unable to find a book with id " + id, e);
            throw new UnableToFindException("No such book found! " + e.getMessage());
        }
    }

    public Book findByIsbn(String isbn) {
        Connection connection = dataBaseManager.getConnection();
        Book book = new Book();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ISBN)) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            logger.debug("DB query completed");
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "unable to find a book with isbn " + isbn, e);
            throw new UnableToFindException("No such book found! " + e.getMessage());
        }
    }

    public List<Book> findByAuthor(String author) {
        Connection connection = dataBaseManager.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR)) {
            statement.setString(1, author);
            ResultSet result = statement.executeQuery();
            logger.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong("book_id");
                Book book = findById(id);
                books.add(book);
            }
            if (books.isEmpty()) {
                System.out.println("No books by " + author + " found");
            }
            return books;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "no books by " + author + " found");
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Book> findAll() {
        Connection connection = dataBaseManager.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            logger.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong("book_id");
                Book book = findById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "no books found, please check if DB table is empty", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteById(Long id) {
        Connection connection = dataBaseManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            logger.debug("DB query completed");
            if (affectedRows == 1) {
                System.out.println("Deleted book with id : " + id);
                return true;
            } else {
                System.out.println("No such book found!");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "unable to delete", e);
            throw new UnableToDeleteException("Unable to delete! " + e.getMessage());
        }
    }

    private void setParameters(Book book, ResultSet result) {
        try {
            while (result.next()) {
                book.setId(result.getLong("book_id"));
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setIsbn(result.getString("isbn"));
                verifyAndSetCover(book, result.getString("cover"));
                book.setPages(result.getInt("pages"));
                book.setPrice(result.getBigDecimal("price"));
                book.setRating(result.getBigDecimal("rating"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "unable to set parameters", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private void prepareStatementForCreate(Book book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setString(4, book.getCover().toString());
        statement.setInt(5, book.getPages());
        statement.setBigDecimal(6, book.getPrice());
        statement.setBigDecimal(7, book.getRating());
    }

    private void prepareStatementForUpdate(Book book, PreparedStatement statement) throws SQLException {
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getIsbn());
        statement.setString(4, book.getCover().toString());
        statement.setInt(5, book.getPages());
        statement.setBigDecimal(6, book.getPrice());
        statement.setBigDecimal(7, book.getRating());
        statement.setLong(8, book.getId());
    }

    private void verifyAndSetCover(Book book, String cover) {
        switch (cover) {
            case "hard" -> book.setCover(Cover.HARD);
            case "soft" -> book.setCover(Cover.SOFT);
            case "special" -> book.setCover(Cover.SPECIAL);
        }
    }
}
