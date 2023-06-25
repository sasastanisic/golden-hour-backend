package com.goldenhour.domain.user.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "Username can't be blank")
        String username,

        @NotBlank(message = "Password can't be blank")
        String password

) {

}
