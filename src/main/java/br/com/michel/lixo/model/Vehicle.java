package br.com.michel.lixo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_VEHICLE")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

    @Id
    @Column(name = "VEH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VEH_MODEL")
    private String model;

    @Column(name = "VEH_CAPACITY")
    private Double capacity;

    @ManyToMany
    @JoinTable(
            name = "TB_VEHICLE_ROUTE",
            joinColumns = @JoinColumn(name = "VEH_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROU_ID")
    )
    private Set<Route> routes = new HashSet<>();

}
