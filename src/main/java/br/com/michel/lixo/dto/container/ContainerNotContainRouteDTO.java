package br.com.michel.lixo.dto.container;

import br.com.michel.lixo.model.Container;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContainerNotContainRouteDTO {

    private Long id;

    @NotNull(message = "Capacity is required!")
    private Double capacity;

    public ContainerNotContainRouteDTO(Container containerEntity) {
        this.id = containerEntity.getId();
        this.capacity = containerEntity.getCapacity();
    }
}
