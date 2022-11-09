package online.javaclass.bookstore.controller;

import java.io.PrintStream;

public interface Controller {
    void process(String request, PrintStream response);
}
