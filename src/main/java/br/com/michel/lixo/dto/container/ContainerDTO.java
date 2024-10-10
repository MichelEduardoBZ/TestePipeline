package br.com.michel.lixo.dto.container;

import br.com.michel.lixo.dto.route.RouteDTO;
import br.com.michel.lixo.model.Container;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContainerDTO {

    private Long id;

    @NotNull(message = "Capacity is required!")
    private Double capacity;

    @NotNull(message = "Route is required!")
    private Long routeId;

    private RouteDTO route;

    public ContainerDTO(Double capacity, Long routeId) {
        this.capacity = capacity;
        this.routeId = routeId;
    }

    public ContainerDTO(Container containerEntity) {
        this.id = containerEntity.getId();
        this.capacity = containerEntity.getCapacity();
        this.route = new RouteDTO(containerEntity.getRoute());
    }
}
