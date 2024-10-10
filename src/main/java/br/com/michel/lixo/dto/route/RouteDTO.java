package br.com.michel.lixo.dto.route;

import br.com.michel.lixo.model.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteDTO {

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

    public RouteDTO(String state, String city, String street, String neighborhood, Integer number) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
    }

    public RouteDTO(Route entity) {
        this.id = entity.getId();
        this.state = entity.getState();
        this.city = entity.getCity();
        this.street = entity.getStreet();
        this.neighborhood = entity.getNeighborhood();
        this.number = entity.getNumber();
    }
}
