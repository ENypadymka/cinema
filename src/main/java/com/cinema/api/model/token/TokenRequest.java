package com.cinema.api.model.token;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * GetTokenRequest.
 */
public class TokenRequest {

    @Email
    @Size(max = 30)
    private String email;

    @Size(max = 30)
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
