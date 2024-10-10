package br.com.michel.lixo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "E-mail is required!")
    @Email(message = "The user's email is not valid!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres!")
    private String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "E-mail is required!") @Email(message = "The user's email is not valid!") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "E-mail is required!") @Email(message = "The user's email is not valid!") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required!") @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres!") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required!") @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres!") String password) {
        this.password = password;
    }
}