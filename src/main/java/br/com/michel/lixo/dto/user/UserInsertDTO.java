package br.com.michel.lixo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

public class UserInsertDTO extends UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 20, message = "The password must contain between 6 and 20 characters!")
    private String password;

    public UserInsertDTO(String name, String email, LocalDate birthdate, String password) {
        super(name, email, birthdate);
        this.password = password;
    }

    public UserInsertDTO() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

