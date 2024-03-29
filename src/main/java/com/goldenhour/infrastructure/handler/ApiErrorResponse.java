package com.goldenhour.infrastructure.handler;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(

        String message,
        HttpStatus status,
        String timestamp

) {

}
