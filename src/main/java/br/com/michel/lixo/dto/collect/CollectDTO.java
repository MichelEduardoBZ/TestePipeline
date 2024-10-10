package br.com.michel.lixo.dto.collect;

import br.com.michel.lixo.dto.container.ContainerDTO;
import br.com.michel.lixo.dto.route.RouteDTO;
import br.com.michel.lixo.dto.vehicle.VehicleDTO;
import br.com.michel.lixo.model.Collect;
import br.com.michel.lixo.model.CollectionStatus;
import br.com.michel.lixo.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CollectDTO {

    private Long id;

    @NotNull(message = "Date the collect is required!")
    private LocalDate collectionDate;

    @NotNull(message = "Weight is required!")
    private Double weight;

    @NotNull(message = "Container is required!")
    private Long containerId;

    private CollectionStatus collectionStatus;

    private ContainerDTO containerDTO;

    private RouteDTO routeDTO;

    private VehicleDTO vehicleDTO;

    public CollectDTO(LocalDate collectionDate, Double weight, Long containerId) {
        this.collectionDate = collectionDate;
        this.weight = weight;
        this.containerId = containerId;
    }

    public CollectDTO(Collect collectEntity) {
        this.id = collectEntity.getId();
        this.collectionDate = collectEntity.getCollectionDate();
        this.weight = collectEntity.getWeight();
        this.collectionStatus = collectEntity.getCollectionStatus();
        this.containerDTO = new ContainerDTO(collectEntity.getContainer());
        this.routeDTO = new RouteDTO(collectEntity.getRoute());
        this.vehicleDTO = new VehicleDTO(collectEntity.getVehicle());
    }
}
