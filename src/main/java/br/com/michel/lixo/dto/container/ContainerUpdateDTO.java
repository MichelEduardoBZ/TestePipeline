package br.com.michel.lixo.dto.container;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContainerUpdateDTO {

    private Double capacity;

    public ContainerUpdateDTO(Double capacity) {
        this.capacity = capacity;
    }
}
