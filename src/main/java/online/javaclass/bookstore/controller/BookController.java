package online.javaclass.bookstore.controller;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    //    private final BookControllerUtils bookControllerUtils;
    private final PagingUtil pagingUtil;

    @GetMapping(path = "/one")
    public String getById(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        BookDto book = service.getById(id);
        req.setAttribute("book", book);
        return "book";
    }

    @GetMapping(path = "/all")
    public String getAll(HttpServletRequest req) {
        List<BookDto> books;
        PageableDto pageable = pagingUtil.getPageable(req);
        books = service.getAll(pageable);
        req.setAttribute("page", pageable.getPage());
        req.setAttribute("total_pages", pageable.getTotalPages());
        req.setAttribute("books", books);
        return "books";
    }

//    @PostMapping(path = "/add")
//    public String create(HttpServletRequest req) {
//        String page;
//        HttpSession session = req.getSession();
//        session.removeAttribute("validationMessages");
//        try {
//            BookDto book = bookControllerUtils.setBookParameters(req);
//            BookDto createdBook = service.create(book);
//            req.setAttribute("book", createdBook);
//            Part filePart = req.getPart("image");
//            if (filePart != null) {
//                String fileName = createdBook.getId().toString() + ".png";
//                filePart.write(bookControllerUtils.getCoverImageUploadDir() + fileName);
//            }
//            page = "controller?command=book&id=" + createdBook.getId();
//        } catch (ValidationException e) {
//            session.setAttribute("validationMessages", e.getMessages());
//            page = "controller?command=add_book_form";
//        } catch (IOException | ServletException e) {
//            throw new RuntimeException(e);
//        }
//        return FrontController.REDIRECT + page;
//    }

//    @RequestMapping(path = "/add_form")
//    public String createForm(HttpServletRequest req) {
//        HttpSession session = req.getSession();
//        session.removeAttribute("validationMessages");
//        return "add_book";
//    }

//    @PostMapping(path = "/edit")
//    public String updateBook(HttpServletRequest req) {
//        String page;
//        HttpSession session = req.getSession();
//        session.removeAttribute("validationMessages");
//        long id = Long.parseLong(req.getParameter("id"));
//        try {
//            BookDto book = bookControllerUtils.setBookParameters(req);
//            book.setId(id);
//            BookDto updatedBook = service.update(book);
//            req.setAttribute("book", updatedBook);
//            page = "controller?command=book&id=" + updatedBook.getId();
//        } catch (ValidationException e) {
//            session.setAttribute("validationMessages", e.getMessages());
//            page = "controller?command=edit_book_form&id=" + id;
//        }
//        return FrontController.REDIRECT + page;
//    }

//    @RequestMapping(path = "/edit_form")
//    public String updateBookForm(HttpServletRequest req) {
//        Long id = Long.parseLong(req.getParameter("id"));
//        BookDto book = service.getById(id);
//        req.setAttribute("book", book);
//        return "edit_book";
//    }
}
