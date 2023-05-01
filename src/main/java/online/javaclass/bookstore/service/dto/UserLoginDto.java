package online.javaclass.bookstore.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginDto {
    @Email(message = "{error.invalid_email}")
    private String email;
    @ToString.Exclude
    @NotBlank(message = "{error.invalid_password}")
//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    public UserLoginDto() {
    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
