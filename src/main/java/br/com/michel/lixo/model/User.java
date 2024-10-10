package br.com.michel.lixo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USE_ID")
    private Long id;

    @Column(name = "USE_NAME")
    private String name;

    @Column(name = "USE_EMAIL")
    private String email;

    @Column(name = "USE_BIRTH_DATE")
    private LocalDate birthdate;

    @Column(name = "USE_PASSWORD")
    private String password;

    @Column(name = "USE_ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(columnDefinition = "DATETIME")
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Container> containers = new ArrayList<>();

    public User() {}

    public User(String name, String email, LocalDate birthdate) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public User(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.birthdate = entity.getBirthdate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.USER) { return List.of(new SimpleGrantedAuthority("ROLE_USER")); }

        return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User User)) return false;
        return Objects.equals(getId(), User.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public User(Long id, String name, String email, LocalDate birthdate, String password, UserRole userRole, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.password = password;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
