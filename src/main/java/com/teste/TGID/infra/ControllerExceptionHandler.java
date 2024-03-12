package com.teste.TGID.infra;


import com.teste.TGID.dto.ExceptionDTO;
import com.teste.TGID.exception.IntegracaoWebhookException;
import com.teste.TGID.exception.InternalServerErrorException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity entradaDuplicada(DataIntegrityViolationException exception) {

        ExceptionDTO exceptionDTO = new ExceptionDTO("Verifique os campos e tente novamente.", "400");
        return ResponseEntity
                .badRequest()
                .body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entrada404(EntityNotFoundException exception) {
        return ResponseEntity
                .notFound()
                .build();

    }

    @ExceptionHandler(IntegracaoWebhookException.class)
    public ResponseEntity handlerIntegracaoWebhookException(IntegracaoWebhookException exception) {
        return ResponseEntity
                .internalServerError()
                .build();

    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity handlerInternalServerErrorException(InternalServerErrorException exception) {
        return ResponseEntity
                .internalServerError()
                .build();

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity entradaExceptionGenerica(Exception exception) {

        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity
                .internalServerError()
                .body(exceptionDTO);


    }
}