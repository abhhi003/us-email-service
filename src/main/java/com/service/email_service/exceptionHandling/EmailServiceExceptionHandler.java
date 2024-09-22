package com.service.email_service.exceptionHandling;

import com.service.openapi.model.EmailServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailServiceExceptionHandler {

    Logger LOG = LoggerFactory.getLogger(EmailServiceExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmailServiceError> handleInputValidationException(Exception e) {
        EmailServiceError error = new EmailServiceError();
        error.setMessage("Invalid email format or missing fields.");
        error.setCode(400);
        LOG.error("Error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EmailServiceError> handleEmailException(Exception e) {
        EmailServiceError error = new EmailServiceError();
        error.setMessage("Internal server error");
        error.setCode(500);
        LOG.error("Error: {}", e.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
