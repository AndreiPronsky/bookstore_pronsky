package online.javaclass.bookstore.data.dao.impl;

import online.javaclass.bookstore.data.connection.DataBaseManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.entities.Cover;
import online.javaclass.bookstore.service.exceptions.UnableToCreateException;
import online.javaclass.bookstore.service.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.exceptions.UnableToUpdateException;
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

    private static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, cover, pages, price, rating)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, isbn = ?," +
            " cover = ?, pages = ?, price = ?, rating = ? WHERE book_id = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE book_id = ?";
    private static final String FIND_BOOK_BY_ISBN = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE isbn = ?";
    private static final String FIND_ALL = "SELECT book_id, title, author, isbn, cover, pages, price, rating FROM books";
    private static final String FIND_BOOKS_BY_AUTHOR = "SELECT book_id, title, author, isbn, cover, pages, price," +
            " rating FROM books WHERE author = ?";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE book_id = ?";
    private static final String COL_BOOK_ID = "book_id";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_ISBN = "isbn";
    private static final String COL_COVER = "cover";
    private static final String COL_PAGES = "pages";
    private static final String COL_PRICE = "price";
    private static final String COL_RATING = "rating";

    private final DataBaseManager dataBaseManager;

    private static final Logger log = LogManager.getLogger();

    public BookDaoImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public Book create(Book book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                book.setId(result.getLong(COL_BOOK_ID));
            }
            return findById(result.getLong(COL_BOOK_ID));
        } catch (SQLException e) {
            throw new UnableToCreateException("Creation failed! " + book, e);
        }
    }

    public Book update(Book book) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            prepareStatementForUpdate(book, statement);
            statement.executeUpdate();
            log.debug("DB query completed");
            return findById(book.getId());
        } catch (SQLException e) {
            throw new UnableToUpdateException("Update failed! " + book, e);
        }
    }

    public Book findById(Long id){
        Book book = new Book();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            setParameters(book, result);
            log.debug("DB query completed");
            return book;
        } catch (SQLException e) {
            throw new UnableToFindException("No such book found! " + book, e);
        }
    }

    public Book findByIsbn(String isbn) {
        Book book = new Book();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ISBN)) {
            statement.setString(1, isbn);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            setParameters(book, result);
            return book;
        } catch (SQLException e) {
            throw new UnableToFindException("No such book found! " + book, e);
        }
    }

    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_AUTHOR)) {
            statement.setString(1, author);
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong(COL_BOOK_ID);
                Book book = findById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("No books by " + author + " found", e);
        }
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            log.debug("DB query completed");
            while (result.next()) {
                long id = result.getLong(COL_BOOK_ID);
                Book book = findById(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("No books found", e);
        }
    }

    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            log.debug("DB query completed");
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new UnableToDeleteException("Unable to delete book with id " + id, e);
        }
    }

    private void setParameters(Book book, ResultSet result) {
        try {
            while (result.next()) {
                book.setId(result.getLong(COL_BOOK_ID));
                book.setTitle(result.getString(COL_TITLE));
                book.setAuthor(result.getString(COL_AUTHOR));
                book.setIsbn(result.getString(COL_ISBN));
                verifyAndSetCover(book, result.getString(COL_COVER));
                book.setPages(result.getInt(COL_PAGES));
                book.setPrice(result.getBigDecimal(COL_PRICE));
                book.setRating(result.getBigDecimal(COL_RATING));
            }
        } catch (SQLException e) {
            throw new RuntimeException("unable to set parameters" + book, e);
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
