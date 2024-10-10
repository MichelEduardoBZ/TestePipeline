package br.com.michel.lixo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TB_COLLECT")
@Getter
@Setter
@NoArgsConstructor
public class Collect {

    @Id
    @Column(name = "COL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COL_COLLECTION_DATE")
    private LocalDate collectionDate;

    @Column(name = "COL_CAPACITY")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "COL_COLLECTION_STATUS")
    private CollectionStatus collectionStatus;

    @ManyToOne
    @JoinColumn(name = "COL_VEHID", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "COL_CONID", nullable = false)
    private Container container;

    @ManyToOne
    @JoinColumn(name = "COL_ROUID", nullable = false)
    private Route route;
}
