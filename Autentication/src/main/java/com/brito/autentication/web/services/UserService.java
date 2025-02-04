package com.brito.autentication.web.services;

import com.brito.autentication.entities.Role;
import com.brito.autentication.entities.User;
import com.brito.autentication.entities.enums.RoleTipo;
import com.brito.autentication.exceptions.EntityNotFoundException;
import com.brito.autentication.exceptions.UsernameUniqueViolationException;
import com.brito.autentication.repositories.RoleRepository;
import com.brito.autentication.repositories.UserRepostory;
import com.brito.autentication.web.controller.UserController;
import com.brito.autentication.web.dto.created.protocol.CreateDTO;
import com.brito.autentication.web.dto.mapper.UserMapper;
import com.brito.autentication.web.dto.responses.UserResponseDefaultDTO;
import com.brito.autentication.web.dto.responses.UserResponseWithCpfDTO;
import com.brito.autentication.web.dto.responses.protocol.ResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepostory userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepositorio;
    private final UserMapper userMapper;
    private final PagedResourcesAssembler<ResponseDTO> assembler;

    @Transactional
    public ResponseDTO createUser(CreateDTO dto) {

        User user = userMapper.toUser(dto);

        try {

            Role role = roleRepositorio.findByRole(RoleTipo.ROLE_OPERATOR).orElse(null);
            if (role == null) {

                role = new Role("ROLE_OPERATOR");
                role = roleRepositorio.save(role);

            }

            user.addRole(role);
            String senhaEncriptada = passwordEncoder.encode(user.getPassword());
            user.setPassword(senhaEncriptada);
            user = userRepository.save(user);
            UserResponseDefaultDTO response = ((UserResponseDefaultDTO) userMapper.toDto(user,
                    UserResponseDefaultDTO.class))
                    .add(linkTo(methodOn(UserController.class).createUser(dto)).withSelfRel());

            return response;

        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(
                    String.format("Already registered user %s.", user.getUsername()));
        }

    }

    @Transactional
    public ResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%s not found.", id)));
        var response = (UserResponseDefaultDTO) userMapper.toDto(user, UserResponseDefaultDTO.class);
        response.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return response;

    }

    @Transactional
    public PagedModel<EntityModel<ResponseDTO>> getAll(Integer page, Integer limit, String sort) {

        var direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, direction);
        Page<User> users = userRepository.findAll(pageable);
        Page<ResponseDTO> response = userMapper.toPageDto(users);
        Link link = linkTo(methodOn(UserController.class).getAll(page, limit, sort)).withSelfRel();

        return assembler.toModel(response, link);

    }

    @Transactional
    public ResponseDTO uptadeById(Long id, User updateUser) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        user.update(updateUser.getPassword(), updateUser.getUsername());
        var response = (UserResponseDefaultDTO) userMapper.toDto(user, UserResponseDefaultDTO.class);
        response.add(linkTo(methodOn(UserController.class).updateById(id, updateUser)).withSelfRel());
        return response;

    }

    @Transactional
    public User getUserByUserName(String user) {
        return (User) userRepository.findByUsername(user).orElseThrow(
                () -> new EntityNotFoundException(String.format("User %s not found", user)));
    }

    public ResponseDTO raiseTheLevel(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User not found")) );
        Role role = roleRepositorio.findByRole(RoleTipo.ROLE_ADMIN).orElse((null));

        if (role == null) {

            role = new Role("ROLE_ADMIN");
            role = roleRepositorio.save(role);

        }

        user.addRole(role);
        user = userRepository.save(user);
        return (ResponseDTO) userMapper.toDto(user, UserResponseWithCpfDTO.class);

    }
}
