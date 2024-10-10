package br.com.michel.lixo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "TB_ROUTE")
@Getter
@Setter
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROU_ID")
    private Long id;

    @Column(name = "ROU_STATE")
    private String state;

    @Column(name = "ROU_CITY")
    private String city;

    @Column(name = "ROU_STREET")
    private String street;

    @Column(name = "ROU_NEIGHBORHOOD")
    private String neighborhood;

    @Column(name = "ROU_NUMBER")
    private Integer number;

    @OneToMany(mappedBy = "route")
    private Set<Collect> collects = new HashSet<>();

    @OneToMany(mappedBy = "route")
    private List<Container> containers = new ArrayList<>();

    @ManyToMany(mappedBy = "routes")
    private Set<Vehicle> vehicles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route route)) return false;
        return Objects.equals(getId(), route.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
