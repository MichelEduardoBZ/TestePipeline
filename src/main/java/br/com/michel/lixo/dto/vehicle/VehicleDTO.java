package br.com.michel.lixo.dto.vehicle;

import br.com.michel.lixo.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleDTO {

    private Long id;

    @NotBlank(message = "model is required!")
    private String model;

    @NotNull(message = "capacity is required!")
    private Double capacity;

    public VehicleDTO(String model, Double capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public VehicleDTO(Vehicle vehicleEntity) {
        this.id = vehicleEntity.getId();
        this.model = vehicleEntity.getModel();
        this.capacity = vehicleEntity.getCapacity();
    }
}
