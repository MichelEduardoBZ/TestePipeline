package br.com.michel.lixo.service;

import br.com.michel.lixo.dto.collect.CollectDTO;
import br.com.michel.lixo.dto.collect.CollectUpdateDTO;
import br.com.michel.lixo.model.*;
import br.com.michel.lixo.repository.CollectRepository;
import br.com.michel.lixo.repository.ContainerRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CollectService {

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Transactional(readOnly = true)
    public Page<CollectDTO> findAllPaged(Pageable pageable) {
        Page<Collect> collects = collectRepository.findAll(pageable);
        return collects.map(CollectDTO::new);
    }

    @Transactional(readOnly = true)
    public CollectDTO findById(Long id) {
        Collect collect = collectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CollectDTO(collect);
    }

    @Transactional
    public CollectDTO insert(CollectDTO collectDTO) {
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectDTO, collect);

        Route route = containerRepository.findByRouteByContainerId(collectDTO.getContainerId());
        Container container = containerRepository.getReferenceById(collectDTO.getContainerId());

        if (collect.getWeight() > container.getCapacity()) {
            throw new ResourceNotFoundException("The collection weight is greater than the capacity of the container!");
        }

        collect.setContainer(container);
        collect.setRoute(route);
        collect.setVehicle(selectVehicleRandomly(container, route.getVehicles()));
        collect.setCollectionStatus(CollectionStatus.PENDING);

        collectRepository.save(collect);
        return new CollectDTO(collect);
    }

    @Transactional
    public CollectDTO update(Long id, CollectUpdateDTO collectUpdateDTO) {
        try {
            Collect collectEntity = collectRepository.getReferenceById(id);

            if (collectEntity.getCollectionStatus().equals(CollectionStatus.COMPLETED)) {
                throw new ResourceNotFoundException("Collect is completeted!");
            }

            BeanUtils.copyProperties(collectUpdateDTO, collectEntity);
            collectEntity = collectRepository.save(collectEntity);
            return new CollectDTO(collectEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!collectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            collectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    public Vehicle selectVehicleRandomly(Container container, Set<Vehicle> vehicleSet) {

        List<Vehicle> vehiclesList = new ArrayList<>();

        for (Vehicle oneVehicle : vehicleSet) {
            if (oneVehicle.getCapacity() >= container.getCapacity()) {
                vehiclesList.add(oneVehicle);
            }
        }

        if (vehiclesList.isEmpty()) {
            throw new ResourceNotFoundException("There is no vehicle with this load capacity available");
        }

        return vehiclesList.get(new Random().nextInt(vehiclesList.size()));
    }

    public void processCollectionStatus(LocalDate currentDate) {
        List<Collect> pendingCollections = collectRepository.findByCollectionDateTimeBeforeOrCollectionDate(currentDate, CollectionStatus.PENDING);
        for (Collect collect : pendingCollections) {
            collect.setCollectionStatus(CollectionStatus.COMPLETED);
            collectRepository.save(collect);
        }
    }
}
