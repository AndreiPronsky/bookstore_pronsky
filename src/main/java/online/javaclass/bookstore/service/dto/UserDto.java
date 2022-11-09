package online.javaclass.bookstore.service.dto;

import online.javaclass.bookstore.data.entities.Role;

import java.math.BigDecimal;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private BigDecimal rating;

    public UserDto() {

    }

    public UserDto(Long id, String firstName, String lastName, String email, String password, Role role, BigDecimal rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.rating = rating;
    }

    public UserDto(String firstName, String lastName, String email, String password, Role role, BigDecimal rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserDto userDto)) return false;
        return Objects.equals(id, userDto.id) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName)
                && Objects.equals(email, userDto.email);
    }

    @Override
    public String toString() {
        return "online.javaclass.bookstore.service.dto.UserDto { id = " + id + " | " +
                "firstname = " + firstName + " | " +
                "lastname = " + lastName + " | " +
                "email = " + email + " | " +
                "password = " + password + " | " +
                "role = " + role + " | " +
                " rating = " + rating + " }";
    }
}
