package br.com.michel.lixo.dto.vehicle;

import br.com.michel.lixo.dto.route.RouteDTO;
import br.com.michel.lixo.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class VehicleWithRouteDTO {

    private Long id;

    @NotBlank(message = "model is required!")
    private String model;

    @NotNull(message = "capacity is required!")
    private Double capacity;

    private List<Long> routesId;

    private Set<RouteDTO> routes = new HashSet<>();

    public VehicleWithRouteDTO(String model, Double capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public VehicleWithRouteDTO(String model, Double capacity, List<Long> routesId) {
        this.model = model;
        this.capacity = capacity;
        this.routesId = routesId;
    }

    public VehicleWithRouteDTO(Vehicle vehicleEntity) {
        this.id = vehicleEntity.getId();
        this.model = vehicleEntity.getModel();
        this.capacity = vehicleEntity.getCapacity();
        this.routes = vehicleEntity.getRoutes().stream().map(RouteDTO::new).collect(Collectors.toSet());
    }
}
