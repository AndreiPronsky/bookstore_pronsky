package online.javaclass.bookstore.data.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude
    private String password;
    private Role role;
    private BigDecimal rating;

    public enum Role {
        USER,
        ADMIN,
        MANAGER
    }
}
