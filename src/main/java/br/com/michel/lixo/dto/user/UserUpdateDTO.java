package br.com.michel.lixo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;

    private String email;

    public UserUpdateDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
