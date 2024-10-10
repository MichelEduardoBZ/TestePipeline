package br.com.michel.lixo.dto.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteUpdateDTO {

    private String state;

    private String city;

    private String street;

    private String neighborhood;

    private Integer number;

    public RouteUpdateDTO(String state, String city, String street, String neighborhood, Integer number) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
    }
}
