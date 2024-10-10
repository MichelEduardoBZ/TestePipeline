package br.com.michel.lixo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TB_CONTAINER")
@Getter
@Setter
@NoArgsConstructor
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON_ID")
    private Long id;

    @Column(name = "CON_CAPACITY")
    private Double capacity;

    @OneToMany(mappedBy = "container")
    private Set<Collect> collects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "CON_ROUID")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "CON_USERID")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Container container)) return false;
        return Objects.equals(getId(), container.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
