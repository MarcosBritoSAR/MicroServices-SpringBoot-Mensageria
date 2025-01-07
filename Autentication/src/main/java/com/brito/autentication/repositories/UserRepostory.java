package com.brito.autentication.repositories;

import com.brito.autentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepostory extends JpaRepository<User,Long>{
    Optional<UserDetails> findByUser(String username);
}
