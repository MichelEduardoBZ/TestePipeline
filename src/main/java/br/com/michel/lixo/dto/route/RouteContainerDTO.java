package br.com.michel.lixo.dto.route;

import br.com.michel.lixo.dto.container.ContainerNotContainRouteDTO;
import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.model.Route;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RouteContainerDTO {

    @NotNull
    private Long routeId;

    private String routeState;

    private String routeCity;

    private String routeStreet;

    private String routeNeighborhood;

    private Integer routeNumber;

    @NotNull
    private Set<Long> containersId;

    private List<ContainerNotContainRouteDTO> containers = new ArrayList<>();

    public RouteContainerDTO(Long routeId, Set<Long> containersId) {
        this.routeId = routeId;
        this.containersId = containersId;
    }

    public RouteContainerDTO(Route route, List<Container> containers) {
        this.routeId = route.getId();
        this.routeState = route.getState();
        this.routeCity = route.getCity();
        this.routeStreet = route.getStreet();
        this.routeNeighborhood = route.getNeighborhood();
        this.routeNumber = route.getNumber();
        this.containers = containers.stream().map(ContainerNotContainRouteDTO::new).collect(Collectors.toList());
    }
}
