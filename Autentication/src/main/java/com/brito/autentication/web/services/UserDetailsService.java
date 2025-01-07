package com.brito.autentication.web.services;

import com.brito.autentication.exceptions.EntityNotFoundException;
import com.brito.autentication.repositories.UserRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepostory repositorio;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repositorio.findByUser(username).orElseThrow(
        () -> new EntityNotFoundException(String.format("User '%s' not found", username)));
  }

}
