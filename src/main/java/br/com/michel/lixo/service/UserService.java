package br.com.michel.lixo.service;

import br.com.michel.lixo.dto.user.UserContainerDTO;
import br.com.michel.lixo.dto.user.UserDTO;
import br.com.michel.lixo.dto.user.UserInsertDTO;
import br.com.michel.lixo.dto.user.UserUpdateDTO;
import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.model.User;
import br.com.michel.lixo.model.UserRole;
import br.com.michel.lixo.repository.ContainerRepository;
import br.com.michel.lixo.repository.UserRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) {
        Optional<User> userOptional = userRepository.findUserByEmail(userInsertDTO.getEmail());

        if (userOptional.isPresent()) {
            throw new DatabaseException("User with e-mail exists!");
        }

        User user = new User();
        BeanUtils.copyProperties(userInsertDTO, user);

        userInsertDTO.setPassword(new BCryptPasswordEncoder().encode(userInsertDTO.getPassword()));
        user.setPassword(userInsertDTO.getPassword());
        user.setUserRole(UserRole.USER);

        userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        try {
            User userEntity = userRepository.getReferenceById(id);
            BeanUtils.copyProperties(userUpdateDTO, userEntity);
            userEntity = userRepository.save(userEntity);
            return new UserDTO(userEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public UserDTO updatePermissionToAdmin(Long id) {
        try {
            User userEntity = userRepository.getReferenceById(id);
            userEntity.setUserRole(UserRole.ADMIN);
            userEntity = userRepository.save(userEntity);
            return new UserDTO(userEntity);
        } catch (EntityActionVetoException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public UserContainerDTO addUserToContainer(UserContainerDTO userContainerDTO) {

        Optional<User> userEntityOptional = userRepository.findById(userContainerDTO.getUserId());

        if (userEntityOptional.isPresent()) {

            User userEntity = userEntityOptional.get();

            List<Container> containers = userContainerDTO.getContainersId().stream()
                    .map(containerRepository::getReferenceById)
                    .collect(Collectors.toList());

            containers.forEach(oneContainer -> {
                        oneContainer.setUser(userEntity);
                        containerRepository.save(oneContainer);
                    }
            );

            return new UserContainerDTO(userEntity, containers);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userContainerDTO.getUserId());
        }
    }
}
