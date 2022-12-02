package com.example.auctionportal.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Это поле не должно быть пустым")
    @Email(message = "Неправильная почта")
    private String email;
    @Size(min = 8, max = 15, message = "Пароль должен быть от 8 до 15 символов")
    private String password;

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
}
