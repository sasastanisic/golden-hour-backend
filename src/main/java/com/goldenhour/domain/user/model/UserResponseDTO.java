package com.goldenhour.domain.user.model;

public record UserResponseDTO(

        Long id,
        String name,
        String surname,
        int age,
        String email,
        String username,
        String password

) {

}
