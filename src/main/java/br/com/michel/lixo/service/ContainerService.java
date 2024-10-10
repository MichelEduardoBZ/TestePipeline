package br.com.michel.lixo.service;

import br.com.michel.lixo.dto.container.ContainerDTO;
import br.com.michel.lixo.dto.container.ContainerUpdateDTO;
import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.repository.ContainerRepository;
import br.com.michel.lixo.repository.RouteRepository;
import br.com.michel.lixo.repository.UserRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ContainerDTO> findAllPaged(Pageable pageable) {
        Page<Container> containers = containerRepository.findAll(pageable);
        return containers.map(ContainerDTO::new);
    }

    @Transactional(readOnly = true)
    public ContainerDTO findById(Long id) {
        Container container = containerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ContainerDTO(container);
    }

    @Transactional
    public ContainerDTO insert(ContainerDTO containerDTO) {
        Container container = new Container();
        container.setRoute(routeRepository.findById(containerDTO.getRouteId()).orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
        BeanUtils.copyProperties(containerDTO, container);
        containerRepository.save(container);
        return new ContainerDTO(container);
    }

    @Transactional
    public ContainerDTO update(Long id, ContainerUpdateDTO containerUpdateDTO) {
        try {
            Container containerEntity = containerRepository.getReferenceById(id);
            BeanUtils.copyProperties(containerUpdateDTO, containerEntity);
            containerEntity = containerRepository.save(containerEntity);
            return new ContainerDTO(containerEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!containerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            containerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
