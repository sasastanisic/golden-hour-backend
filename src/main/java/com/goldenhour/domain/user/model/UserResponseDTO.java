package com.goldenhour.domain.user.model;

import com.goldenhour.domain.user.enums.Role;

public record UserResponseDTO(

        Long id,
        String name,
        String surname,
        int age,
        String email,
        String username,
        String password,
        Role role

) {

}
