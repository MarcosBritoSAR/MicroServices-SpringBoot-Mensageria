package com.brito.autentication.web.services;


import com.brito.autentication.entities.Role;
import com.brito.autentication.entities.User;
import com.brito.autentication.entities.enums.RoleTipo;
import com.brito.autentication.exceptions.EntityNotFoundException;
import com.brito.autentication.exceptions.UsernameUniqueViolationException;
import com.brito.autentication.repositories.RoleRepository;
import com.brito.autentication.repositories.UserRepostory;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepostory userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepositorio;
    @Transactional
    public User salvar(User user) {
        try {

            Role role = roleRepositorio.findByRole(RoleTipo.ROLE_OPERATOR).orElse(null);

            if (role == null) {

                role = new Role("ROLE_OPERATOR");
                role = roleRepositorio.save(role);
            }

            Set<Role> roles = new HashSet<>(Arrays.asList(role));
            user.setRoles(roles);

            String senhaEncriptada = passwordEncoder.encode(user.getPassword());
            user.setPassword(senhaEncriptada);

            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(
                    String.format("Already registered user %s.", user.getUsername()));
        }
    }

    @Transactional
    public User buscarUserPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%s not found.", id)));
    }

    @Transactional
    public List<User> buscarUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User atualizUser(Long id, User updateUser) {

        // TODO: Verificar a regra de negócio: O usuário tem permissão para atualizar o
        // e-mail?

        User user = userRepository.findById(id).orElse(null);

        user.setUser (updateUser.getUser());
        user.setPassword(updateUser.getPassword());
        return userRepository.save(user);

    }

    @Transactional
    public User buscarUserPorUserName(String user) {
        return (User) userRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException(String.format("User %s not found" , user)));
    }


}
