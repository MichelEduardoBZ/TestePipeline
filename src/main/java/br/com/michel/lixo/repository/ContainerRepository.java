package br.com.michel.lixo.repository;

import br.com.michel.lixo.model.Container;
import br.com.michel.lixo.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {

    @Query("SELECT c.route FROM Container c WHERE c.id = :containerId")
    Route findByRouteByContainerId(Long containerId);
}
