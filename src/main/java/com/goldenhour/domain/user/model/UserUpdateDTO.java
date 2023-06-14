package com.goldenhour.domain.user.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(

        @NotBlank(message = "Name can't be blank")
        String name,

        @NotBlank(message = "Surname can't be blank")
        String surname,

        @Min(value = 0, message = "Age can't be negative")
        int age,

        @NotBlank(message = "Password can't be blank")
        String password,

        @NotBlank(message = "Confirmed password can't be blank")
        String confirmedPassword

) {

}
