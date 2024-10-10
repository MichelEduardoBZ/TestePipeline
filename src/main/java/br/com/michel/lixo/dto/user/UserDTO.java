package br.com.michel.lixo.dto.user;

import br.com.michel.lixo.model.User;
import br.com.michel.lixo.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Username is required!")
    String name;

    @NotBlank(message = "E-mail is required!")
    @Email(message = "The user's email is not valid!")
    private String email;

    @NotNull(message = "User's date of birth is required!")
    private LocalDate birthdate;

    private UserRole userRole;

    public UserDTO(String name, String email, LocalDate birthdate) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
        this.userRole = user.getUserRole();
    }
}
