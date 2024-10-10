package br.com.michel.lixo.service;

import br.com.michel.lixo.dto.route.RouteContainerDTO;
import br.com.michel.lixo.dto.route.RouteDTO;
import br.com.michel.lixo.dto.route.RouteUpdateDTO;
import br.com.michel.lixo.dto.route.RouteVehicleDTO;
import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.model.Route;
import br.com.michel.lixo.model.Vehicle;
import br.com.michel.lixo.repository.ContainerRepository;
import br.com.michel.lixo.repository.RouteRepository;
import br.com.michel.lixo.repository.VehicleRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Transactional(readOnly = true)
    public Page<RouteDTO> findAllPaged(Pageable pageable) {
        Page<Route> routes = routeRepository.findAll(pageable);
        return routes.map(RouteDTO::new);
    }

    @Transactional(readOnly = true)
    public RouteDTO findById(Long id) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RouteDTO(route);
    }

    @Transactional
    public RouteDTO insert(RouteDTO routeDTO) {
        Route route = new Route();
        BeanUtils.copyProperties(routeDTO, route);
        routeRepository.save(route);
        return new RouteDTO(route);
    }

    @Transactional
    public RouteVehicleDTO insertRouteWithVehicle(RouteVehicleDTO routeVehicleDTO) {
        Route route = new Route();

        if (!routeVehicleDTO.getVehiclesId().isEmpty()) {
            Set<Vehicle> vehicles = routeVehicleDTO.getVehiclesId().stream()
                    .map(vehicleRepository::getReferenceById)
                    .collect(Collectors.toSet());
            route.getVehicles().addAll(vehicles);
        }

        BeanUtils.copyProperties(routeVehicleDTO, route);
        routeRepository.save(route);
        return new RouteVehicleDTO(route);
    }

    @Transactional
    public RouteDTO update(Long id, RouteUpdateDTO routeUpdateDTO) {
        try {
            Route routeEntity = routeRepository.getReferenceById(id);
            BeanUtils.copyProperties(routeUpdateDTO, routeEntity);
            routeEntity = routeRepository.save(routeEntity);
            return new RouteDTO(routeEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            routeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public RouteContainerDTO addRouteToContainer(RouteContainerDTO routeContainerDTO) {

        Optional<Route> routeEntityOptional = routeRepository.findById(routeContainerDTO.getRouteId());

        if (routeEntityOptional.isPresent()) {

            Route routeEntity = routeEntityOptional.get();

            List<Container> containers = routeContainerDTO.getContainersId().stream()
                    .map(containerRepository::getReferenceById)
                    .collect(Collectors.toList());

            containers.forEach(oneContainer -> {
                        oneContainer.setRoute(routeEntity);
                        containerRepository.save(oneContainer);
                    }
            );

            return new RouteContainerDTO(routeEntity, containers);
        } else {
            throw new ResourceNotFoundException("Route not found with ID: " + routeContainerDTO.getRouteId());
        }
    }
}
