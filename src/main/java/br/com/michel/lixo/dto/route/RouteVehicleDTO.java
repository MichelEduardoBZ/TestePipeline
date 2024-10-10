package br.com.michel.lixo.dto.route;

import br.com.michel.lixo.dto.vehicle.VehicleDTO;
import br.com.michel.lixo.model.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RouteVehicleDTO {

    private Long id;

    @NotBlank(message = "State is required!")
    private String state;

    @NotBlank(message = "City is required!")
    private String city;

    @NotBlank(message = "Street is required!")
    private String street;

    @NotBlank(message = "Neighborhood is required!")
    private String neighborhood;

    @NotNull(message = "Number is required!")
    private Integer number;

    private Set<Long> vehiclesId = new HashSet<>();

    private Set<VehicleDTO> vehicles = new HashSet<>();

    public RouteVehicleDTO(String state, String city, String street, String neighborhood, Integer number) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
    }

    public RouteVehicleDTO(String state, String city, String street, String neighborhood, Integer number, Set<Long> vehiclesId) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
        this.vehiclesId = vehiclesId;
    }

    public RouteVehicleDTO(Route entity) {
        this.id = entity.getId();
        this.state = entity.getState();
        this.city = entity.getCity();
        this.street = entity.getStreet();
        this.neighborhood = entity.getNeighborhood();
        this.number = entity.getNumber();

        if (entity.getVehicles() != null) {
            this.vehicles = entity.getVehicles().stream().map(VehicleDTO::new).collect(Collectors.toSet());
        }
    }
}
