package com.brito.autentication.entities;

import com.brito.autentication.entities.enums.RoleTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true)
    private RoleTipo role;


    @Override
    public String toString(){
        return role.name();
    }

    public Role(String role) {
        this.role = RoleTipo.valueOf(role);
    }

    public Role(RoleTipo role) {
        this.role = role;
    }

    public Role(long id, String role) {
        this.role = RoleTipo.valueOf(role);
        this.id = id;
    }
}
