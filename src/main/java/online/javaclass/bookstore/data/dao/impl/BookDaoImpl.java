package online.javaclass.bookstore.data.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private static final String CREATE_BOOK = "INSERT INTO books (title, author, isbn, genre_id, cover_id, pages, price, rating) " +
            "VALUES (?, ?, ?, (SELECT g.id FROM genres g WHERE g.name = ?), " +
            "(SELECT c.id FROM covers c WHERE c.name = ?), ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = :title, author = :author, isbn = :isbn, " +
            "genre_id = (SELECT g.id FROM genres g WHERE g.name = :genre), " +
            "cover_id = (SELECT c.id FROM covers c WHERE c.name = :cover), pages = :pages, price = :price, " +
            "rating = :rating WHERE id = :id";
    private static final String FIND_BOOK_BY_ID = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c on b.cover_id = c.id WHERE b.id = ?";
    private static final String FIND_BOOK_BY_ISBN = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE isbn = ?";
    private static final String FIND_ALL_BOOKS_PAGED = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c on b.cover_id = c.id " +
            "ORDER BY b.id LIMIT :limit OFFSET :offset ";
    private static final String FIND_BOOKS_BY_AUTHOR_PAGED = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre, " +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b" +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE author = :author " +
            "ORDER BY b.id LIMIT :limit OFFSET :offset";

    private static final String SEARCH = "SELECT b.id, b.title, b.author, b.isbn, g.name AS genre," +
            "c.name AS cover, b.pages, b.price, b.rating FROM books b " +
            "JOIN genres g ON b.genre_id = g.id " +
            "JOIN covers c ON b.cover_id = c.id WHERE b.title LIKE :input OR b.author LIKE :input";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String COUNT_BOOKS = "SELECT count(*) FROM books";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_ISBN = "isbn";
    private static final String COL_GENRE = "genre";
    private static final String COL_COVER = "cover";
    private static final String COL_PAGES = "pages";
    private static final String COL_PRICE = "price";
    private static final String COL_RATING = "rating";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MessageManager messageManager;

    @Override
    public List<BookDto> search(String input) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("input", input);
            return namedParameterJdbcTemplate.query(SEARCH, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing")
                    + " " + input + messageManager.getMessage("in_title"));
        }
    }

    @Override
    public Long count() {
        try {
            return jdbcTemplate.queryForObject(COUNT_BOOKS, Long.class);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new AppException(messageManager.getMessage("count_failed"));
        }
    }

    @Override
    public BookDto create(BookDto book) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getAuthor());
                ps.setString(3, book.getIsbn());
                ps.setString(4, book.getGenre().toString());
                ps.setString(5, book.getCover().toString());
                ps.setInt(6, book.getPages());
                ps.setBigDecimal(7, book.getPrice());
                ps.setBigDecimal(8, book.getRating());
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                return getById((Long)(keyHolder.getKey()));
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage() + e);
//            if (e.getMessage().startsWith("ERROR: duplicate key value violates unique constraint")) {
//                throw new UnableToCreateException(messageManager.getMessage("error.isbn_in_use"));
//            }
            throw new UnableToCreateException(messageManager.getMessage("book.unable_to_create"));
        }
    }

    @Override
    public BookDto update(BookDto book) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_BOOK, getParamMap(book));
            return getById(book.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToUpdateException(messageManager.getMessage("book.unable_to_update"));
        }
    }

    @Override
    public BookDto getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BOOK_BY_ID, this::process, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id"));
        }
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        try {
            return jdbcTemplate.queryForObject(FIND_BOOK_BY_ISBN, this::process, isbn);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn"));
        }
    }

    @Override
    public List<BookDto> getByAuthor(String author, int limit, int offset) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("author", author);
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_BOOKS_BY_AUTHOR_PAGED, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author"));
        }
    }

    @Override
    public List<BookDto> getAll(int limit, int offset) {
        try {
            Map<String, Integer> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            return namedParameterJdbcTemplate.query(FIND_ALL_BOOKS_PAGED, params, this::process);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find"));
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            return 1 == jdbcTemplate.update(DELETE_BOOK_BY_ID, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage() + e);
            throw new UnableToDeleteException(messageManager.getMessage("book.unable_to_delete") + id);
        }
    }

    public BookDto process(ResultSet rs, int rowNum) throws SQLException {
        BookDto book = new BookDto();
        book.setId(rs.getLong(COL_ID));
        book.setTitle(rs.getString(COL_TITLE));
        book.setAuthor(rs.getString(COL_AUTHOR));
        book.setIsbn(rs.getString(COL_ISBN));
        book.setGenre(BookDto.Genre.valueOf(rs.getString(COL_GENRE)));
        book.setCover(BookDto.Cover.valueOf(rs.getString(COL_COVER)));
        book.setPages(rs.getInt(COL_PAGES));
        book.setPrice(rs.getBigDecimal(COL_PRICE));
        book.setRating(rs.getBigDecimal(COL_RATING));
        return book;
    }

    private Map<String, Object> getParamMap(BookDto book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("isbn", book.getIsbn());
        params.put("genre", book.getGenre().toString());
        params.put("cover", book.getCover().toString());
        params.put("pages", book.getPages());
        params.put("price", book.getPrice());
        params.put("rating", book.getRating());
        return params;
    }
}