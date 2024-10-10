package br.com.michel.lixo.dto.collect;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CollectUpdateDTO {

    private LocalDate collectionDateTime;

    private Double weight;

    public CollectUpdateDTO(LocalDate collectionDateTime, Double weight) {
        this.collectionDateTime = collectionDateTime;
        this.weight = weight;
    }
}
