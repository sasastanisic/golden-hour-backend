package com.goldenhour.domain.user.service;

import com.goldenhour.domain.user.entity.User;
import com.goldenhour.domain.user.model.PasswordDTO;
import com.goldenhour.domain.user.model.UserRequestDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;
import com.goldenhour.domain.user.model.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userDTO);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(Long id);

    User getById(Long id);

    Optional<User> getUserByUsername(String username);

    void existsById(Long id);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userDTO);

    UserResponseDTO updatePassword(Long id, PasswordDTO passwordDTO);

    void deleteUser(Long id);

}
