package online.javaclass.bookstore.controller;

import java.io.PrintStream;

public interface BookController {
    void process(String request, PrintStream response);
    void getById(String request, PrintStream response);
    void getAll(PrintStream response);
    void create(String request, PrintStream response);
    void getByIsbn(String request, PrintStream response);
    void getByAuthor(String request, PrintStream response);
    void update(String request, PrintStream response);
    void delete(String request, PrintStream response);
}
