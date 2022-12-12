package com.example.auctionportal.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {
    @NotBlank(message = "Это поле должно быть заполнено")
    @Pattern(regexp = "[a-zA-Z \\\\u0400-\\\\u04FF]", message = "Я не знаю таких имен")
    private String firstName;
    @NotBlank(message = "Это поле должно быть заполнено")
    @Pattern(regexp = "[a-zA-Z \\\\u0400-\\\\u04FF]", message = "Я не знаю таких фамилий")
    private String lastName;
    @NotBlank(message = "Это поле должно быть заполнено")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$", message = "Неправильная почта")
    private String email;
    @Size(min = 8, max = 15, message = "Пароль должен содержать от 8 до 15 символов")
    private String password;
    private Set<String> roles;

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
