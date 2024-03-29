package com.goldenhour.domain.user.model;

import jakarta.validation.constraints.NotBlank;

public record PasswordDTO(

        @NotBlank(message = "Password can't be blank")
        String password,

        @NotBlank(message = "Confirmed password can't be blank")
        String confirmedPassword

) {

}
