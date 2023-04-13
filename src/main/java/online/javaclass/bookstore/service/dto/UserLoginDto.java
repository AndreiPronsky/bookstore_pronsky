package online.javaclass.bookstore.service.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserLoginDto {
    @Email
    private String email;
    @Length()
    private String password;

    public UserLoginDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
