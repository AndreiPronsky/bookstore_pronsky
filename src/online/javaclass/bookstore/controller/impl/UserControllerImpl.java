package online.javaclass.bookstore.controller.impl;

import online.javaclass.bookstore.controller.Controller;
import online.javaclass.bookstore.data.entities.Role;
import online.javaclass.bookstore.service.UserService;
import online.javaclass.bookstore.service.dto.UserDto;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

public class UserControllerImpl implements Controller {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public void process(String request, PrintStream response) {
        if (request.startsWith("get all")) {
            getAll(response);
        } else if (request.startsWith("create ")) {
            create(request, response);
        } else if (request.startsWith("update ")) {
            update(request, response);
        } else if (request.startsWith("delete ")) {
            delete(request, response);
        } else if (request.matches("^get \\d+$")) {
            getById(request, response);
        } else if (request.matches("^get [a-zA-Z]+$")) {
            getByLastName(request, response);
        } else if (request.matches("^get [a-zA-Z0-9_!#$%&'*+=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-z]+$")) {
            getByEmail(request, response);
        } else {
            response.println("UNKNOWN COMMAND!");
        }
    }

    private void getById(String request, PrintStream response) {
        Long id = Long.parseLong(request.split(" ")[1]);
        UserDto user = userService.getById(id);
        response.println("USER : ");
        response.println(user);
    }

    private void getAll(PrintStream response) {
        List<UserDto> users = userService.getAll();
        response.println("ALL USERS : ");
        for (UserDto user : users) {
            response.println(user);
        }
        response.println("END OF LIST");
    }

    private void create(String request, PrintStream response) {
        String userData = request.substring(7);
        String[] data = userData.split(", ");
        String firstName = data[0];
        String lastName = data[1];
        String email = data[2];
        String password = data[3];
        Role role = Role.valueOf(data[4]);
        BigDecimal rating = BigDecimal.valueOf(Double.parseDouble(data[5]));
        UserDto user = new UserDto(firstName, lastName, email, password, role, rating);
        userService.create(user);
        response.println("CREATED USER : ");
        response.println(user);
    }

    private void getByEmail(String request, PrintStream response) {
        String email = request.substring(4);
        UserDto user = userService.getByEmail(email);
        response.println("USER : ");
        response.println(user);
    }

    private void getByLastName(String request, PrintStream response) {
        String lastName = request.substring(4);
        List<UserDto> users = userService.getByLastName(lastName);
        response.println("USERS WITH LASTNAME " + "\"" + lastName + "\"" + " :");
        for (UserDto user : users) {
            response.println(user);
        }
        response.println("END OF LIST");
    }

    private void update(String request, PrintStream response) {
        String[] data = request.substring(7).split(", ");
        Long id = Long.parseLong(data[0]);
        UserDto user = userService.getById(id);
        userService.update(user);
        response.println("UPDATED USER WITH ID : " + id);
    }

    private void delete(String request, PrintStream response) {
        Long id = Long.parseLong(request.split(" ")[1]);
        userService.deleteById(id);
        response.println("DELETED USER WITH ID : " + id);
    }
}
