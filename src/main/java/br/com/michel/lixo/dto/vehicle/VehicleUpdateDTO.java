package br.com.michel.lixo.dto.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleUpdateDTO {

    private String model;

    private Double capacity;

    public VehicleUpdateDTO(String model, Double capacity) {
        this.model = model;
        this.capacity = capacity;
    }
}
