package com.sumber.barokah.jurnal.controller.master;

import com.sumber.barokah.jurnal.dto.WebResponse;
import com.sumber.barokah.jurnal.utilities.Constants;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder()
                        .errors(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .status_code(Constants.BAD_REQUEST)
                        .message(Constants.VALIDATION_MESSAGE)
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException exception) {

        if (exception.getStatusCode().value() == 400) {
            return ResponseEntity.status(exception.getStatusCode())
                    .body(WebResponse.<String>builder()
                            .errors(exception.getReason())
                            .status(HttpStatus.BAD_REQUEST)
                            .status_code(exception.getStatusCode().value())
                            .message(Constants.BAD_REQUEST_MESSAGE)
                            .build());
        }

        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<String>builder()
                        .errors(exception.getReason())
                        .status(HttpStatus.NOT_FOUND)
                        .status_code(exception.getStatusCode().value())
                        .message(Constants.BAD_REQUEST_MESSAGE)
                        .build());
    }

}
