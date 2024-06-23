package com.eduortza.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailSuscriber {

    private long id;
    private String email;
    private String token;

    public boolean isValidEmail() {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
