package com.goldenhour.domain.user.service;

import com.goldenhour.domain.user.entity.User;
import com.goldenhour.domain.user.model.UserRequestDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;
import com.goldenhour.domain.user.model.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userDTO);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(Long id);

    User getById(Long id);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userDTO);

    void deleteUser(Long id);

}
