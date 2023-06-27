package com.goldenhour.domain.user.service;

import com.goldenhour.domain.user.entity.User;
import com.goldenhour.domain.user.enums.Role;
import com.goldenhour.domain.user.model.PasswordDTO;
import com.goldenhour.domain.user.model.UserRequestDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;
import com.goldenhour.domain.user.model.UserUpdateDTO;
import com.goldenhour.domain.user.repository.UserRepository;
import com.goldenhour.infrastructure.handler.exceptions.ConflictException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import com.goldenhour.infrastructure.mapper.UserMapperImpl;
import com.goldenhour.infrastructure.security.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    User user;

    Page<User> users;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapperImpl userMapper;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Sasa");
        user.setSurname("Stanisic");
        user.setAge(22);
        user.setEmail("sasastanisic4@gmail.com");
        user.setUsername("salethegoat23");
        user.setPassword("sasa123");
        user.setRole(Role.USER);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        users = new PageImpl<>(userList);

        userService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void testCreateUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", "sasa123");
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", Role.USER);

        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        when(passwordEncoder.encode(userRequestDTO.password())).thenReturn(userResponseDTO.password());
        when(userRepository.existsByEmailOrUsername(userRequestDTO.email(), null)).thenReturn(false);
        when(userRepository.existsByEmailOrUsername(null, userRequestDTO.username())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        doReturn(userResponseDTO).when(userMapper).toUserResponseDTO(user);

        var createdUserDTO = userService.createUser(userRequestDTO);

        assertThat(userResponseDTO).usingRecursiveComparison().isEqualTo(createdUserDTO);
    }

    @Test
    void testUserExistsByEmail() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", "sasa123");

        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        doReturn(false).when(userRepository).existsByEmailOrUsername("sasastanisic4@gmail.com", null);

        Assertions.assertDoesNotThrow(() -> userService.createUser(userRequestDTO));
    }

    @Test
    void testUserExistsByEmail_NotValid() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", "sasa123");

        when(userMapper.toUser(userRequestDTO)).thenReturn(user);
        doReturn(true).when(userRepository).existsByEmailOrUsername("sasastanisic4@gmail.com", null);

        Assertions.assertThrows(ConflictException.class, () -> userService.createUser(userRequestDTO));
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = mock(Pageable.class);
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", Role.USER);

        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);
        var expectedUsers = users.map(user -> userMapper.toUserResponseDTO(user));
        doReturn(users).when(userRepository).findAll(pageable);
        var userPage = userService.getAllUsers(pageable);

        Assertions.assertEquals(expectedUsers, userPage);
    }

    @Test
    void testGetUserById() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa123", Role.USER);

        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);
        var expectedUser = userMapper.toUserResponseDTO(user);
        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        var returnedUser = userService.getUserById(1L);

        Assertions.assertEquals(expectedUser, returnedUser);
    }

    @Test
    void testGetUserById_NotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByUsername() {
        doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());
        var optionalUser = userService.getUserByUsername(user.getUsername());

        Assertions.assertTrue(optionalUser.isPresent());
    }

    @Test
    void testGetUserByUsername_NotFound() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());
        var optionalUser = userService.getUserByUsername(user.getUsername());

        Assertions.assertTrue(optionalUser.isEmpty());
    }

    @Test
    void testUserExists() {
        doReturn(true).when(userRepository).existsById(1L);
        Assertions.assertDoesNotThrow(() -> userService.existsById(1L));
        verify(userRepository, times(1)).existsById(1L);
    }

    @Test
    void testUserExists_NotFound() {
        doReturn(false).when(userRepository).existsById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> userService.existsById(1L));
    }

    @Test
    void testUpdateUser() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Sasa", "Stanisic", 22, "sasa", "sasa");
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa", Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doCallRealMethod().when(userMapper).updateUserFromDTO(userUpdateDTO, user);
        doNothing().when(authenticationService).canUserAccess(user.getUsername(), "You don't have permission to update user account");
        when(passwordEncoder.encode(userUpdateDTO.password())).thenReturn(userResponseDTO.password());
        when(userRepository.save(user)).thenReturn(user);
        doReturn(userResponseDTO).when(userMapper).toUserResponseDTO(user);

        var updatedUserDTO = userService.updateUser(1L, userUpdateDTO);

        assertThat(userResponseDTO).usingRecursiveComparison().isEqualTo(updatedUserDTO);
    }

    @Test
    void testUpdatePassword() {
        PasswordDTO passwordDTO = new PasswordDTO("sasa", "sasa");
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Sasa", "Stanisic", 22, "sasastanisic4@gmail.com",
                "salethegoat23", "sasa", Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(authenticationService).canUserAccess(user.getUsername(), "You don't have permission to update password");
        when(passwordEncoder.encode(passwordDTO.password())).thenReturn(userResponseDTO.password());
        when(userRepository.save(user)).thenReturn(user);
        doReturn(userResponseDTO).when(userMapper).toUserResponseDTO(user);

        var updatedUserDTO = userService.updatePassword(1L, passwordDTO);

        assertThat(userResponseDTO).usingRecursiveComparison().isEqualTo(updatedUserDTO);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void testDeleteUser_NotFound() {
        doReturn(false).when(userRepository).existsById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));
    }

}
