package com.eduortza.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String username;
    private String password;

    public boolean isValidPassword() {
        return password.length() >= 8;
    }

    public boolean isValidNickname() {
        return username.length() >= 4;
    }

    public boolean isValid() {
        return isValidNickname() && isValidPassword();
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }
}
