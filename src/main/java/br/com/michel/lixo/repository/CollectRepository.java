package br.com.michel.lixo.repository;

import br.com.michel.lixo.model.Collect;
import br.com.michel.lixo.model.CollectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

    @Query("SELECT c FROM Collect c WHERE c.collectionDate <= :currentDate AND c.collectionStatus = :collectionStatus")
    List<Collect> findByCollectionDateTimeBeforeOrCollectionDate(LocalDate currentDate, CollectionStatus collectionStatus);
}
