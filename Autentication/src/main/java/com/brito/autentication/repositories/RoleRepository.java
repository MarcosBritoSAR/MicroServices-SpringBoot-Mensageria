package com.brito.autentication.repositories;

import java.util.Optional;

import com.brito.autentication.entities.Role;
import com.brito.autentication.entities.enums.RoleTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleTipo role);

    Role findByRole(String string);
    
}
