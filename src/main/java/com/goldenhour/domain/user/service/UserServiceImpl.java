package com.goldenhour.domain.user.service;

import com.goldenhour.domain.user.entity.User;
import com.goldenhour.domain.user.enums.Role;
import com.goldenhour.domain.user.model.UserRequestDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;
import com.goldenhour.domain.user.model.UserUpdateDTO;
import com.goldenhour.domain.user.repository.UserRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_EXISTS = "User with id %d doesn't exist";
    private static final String USER_ALREADY_EXISTS = "User with %s already exists";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        User user = userMapper.toUser(userDTO);

        user.setRole(Role.USER);
        arePasswordsMatching(userDTO.password(), userDTO.confirmedPassword());
        validateEmailAndUsername(userDTO.email(), userDTO.username());
        userRepository.save(user);

        return userMapper.toUserResponseDTO(user);
    }

    private void arePasswordsMatching(String password, String confirmedPassword) {
        if (!password.matches(confirmedPassword)) {
            throw new NotFoundException("Passwords aren't matching");
        }
    }

    private void validateEmailAndUsername(String email, String username) {
        boolean emailExists = userRepository.existsByEmailOrUsername(email, null);
        boolean usernameExists = userRepository.existsByEmailOrUsername(null, username);

        if (emailExists || usernameExists) {
            String conflictMessage;

            if (emailExists && usernameExists) {
                conflictMessage = "email %s and username %s".formatted(email, username);
            } else if (emailExists) {
                conflictMessage = "email %s".formatted(email);
            } else {
                conflictMessage = "username %s".formatted(username);
            }

            throw new ConflictException(USER_ALREADY_EXISTS.formatted(conflictMessage));
        }
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDTO);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return userMapper.toUserResponseDTO(getById(id));
    }

    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException(USER_NOT_EXISTS.formatted(id));
        }

        return optionalUser.get();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void existsById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(USER_NOT_EXISTS.formatted(id));
        }
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userDTO) {
        User user = getById(id);
        userMapper.updateUserFromDTO(userDTO, user);

        arePasswordsMatching(userDTO.password(), userDTO.confirmedPassword());
        userRepository.save(user);

        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        existsById(id);

        userRepository.deleteById(id);
    }

}
