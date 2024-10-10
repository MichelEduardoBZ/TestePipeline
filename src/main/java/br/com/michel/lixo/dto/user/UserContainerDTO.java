package br.com.michel.lixo.dto.user;

import br.com.michel.lixo.dto.container.ContainerDTO;
import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.model.User;
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
public class UserContainerDTO {

    @NotNull
    private Long userId;

    private String userName;

    @NotNull
    private Set<Long> containersId;

    private List<ContainerDTO> containers = new ArrayList<>();

    public UserContainerDTO(Long userId, Set<Long> containersId) {
        this.userId = userId;
        this.containersId = containersId;
    }

    public UserContainerDTO(User user, List<Container> containers) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.containers = containers.stream().map(ContainerDTO::new).collect(Collectors.toList());
    }
}
