package online.javaclass.bookstore.controller;

import online.javaclass.bookstore.data.entities.Role;

import java.io.PrintStream;


public interface UserController {
    void process(String request, PrintStream response);

    void getById(String request, PrintStream response);

    void getAll(PrintStream response);

    void create(String request, PrintStream response);

    void getByEmail(String request, PrintStream response);

    void getByLastName(String request, PrintStream response);

    void update(String request, PrintStream response);

    void delete(String request, PrintStream response);

    Role getRoleFromRequest(String verifyRole);
}
