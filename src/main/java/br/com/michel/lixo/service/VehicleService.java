package br.com.michel.lixo.service;

import br.com.michel.lixo.dto.vehicle.VehicleDTO;
import br.com.michel.lixo.dto.vehicle.VehicleUpdateDTO;
import br.com.michel.lixo.dto.vehicle.VehicleWithRouteDTO;
import br.com.michel.lixo.model.Route;
import br.com.michel.lixo.model.Vehicle;
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

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAllPaged(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        return vehicles.map(VehicleDTO::new);
    }

    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new VehicleDTO(vehicle);
    }

    @Transactional
    public VehicleDTO insert(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();

        BeanUtils.copyProperties(vehicleDTO, vehicle);
        vehicleRepository.save(vehicle);
        return new VehicleDTO(vehicle);
    }

    @Transactional
    public VehicleWithRouteDTO insertVehicleWithRoute(VehicleWithRouteDTO vehicleWithRouteDTO) {
        Vehicle vehicle = new Vehicle();

        if (!vehicleWithRouteDTO.getRoutesId().isEmpty()) {
            Set<Route> routes = vehicleWithRouteDTO.getRoutesId().stream()
                    .map(routeRepository::getReferenceById)
                    .collect(Collectors.toSet());
            vehicle.getRoutes().addAll(routes);
        }

        BeanUtils.copyProperties(vehicleWithRouteDTO, vehicle);
        vehicleRepository.save(vehicle);
        return new VehicleWithRouteDTO(vehicle);
    }

    @Transactional
    public VehicleDTO update(Long id, VehicleUpdateDTO vehicleUpdateDTO) {
        try {
            Vehicle vehicleEntity = vehicleRepository.getReferenceById(id);
            BeanUtils.copyProperties(vehicleUpdateDTO, vehicleEntity);
            vehicleEntity = vehicleRepository.save(vehicleEntity);
            return new VehicleDTO(vehicleEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            vehicleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
