package online.javaclass.bookstore.service.dto;

import lombok.Data;
import online.javaclass.bookstore.data.entities.Role;

import java.math.BigDecimal;
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private BigDecimal rating;
}
