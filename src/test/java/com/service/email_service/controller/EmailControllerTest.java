package com.service.email_service.controller;

import com.service.email_service.service.EmailService;
import com.service.openapi.model.SendEmail200Response;
import com.service.openapi.model.SendEmailRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailParseException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
public class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    public void testSendEmailSuccess() {
        // Arrange
        SendEmailRequest sendEmailRequest = new SendEmailRequest("test@example.com", "Test Subject", "Test Message");
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        // Act
        ResponseEntity<SendEmail200Response> response = emailController.sendEmail(sendEmailRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email sent successfully!", response.getBody().getMessage());
        verify(emailService).sendEmail("test@example.com", "Test Subject", "Test Message");
    }

    @Test
    public void testSendEmailFailure() {
        // Arrange
        SendEmailRequest sendEmailRequest = new SendEmailRequest("test@example.com", "Test Subject", "Test Message");

        doThrow(new MailParseException("Error sending email"))
                .when(emailService)
                .sendEmail(anyString(), anyString(), anyString());

        // Act
        assertThrows(MailParseException.class, () -> emailController.sendEmail(sendEmailRequest));

       // Assert
        verify(emailService).sendEmail("test@example.com", "Test Subject", "Test Message");
    }

    @Test
    public void testSendEmailInvalidRequest() {
        // Arrange
        SendEmailRequest sendEmailRequest = new SendEmailRequest(null, null, null);

        // Act and Assert
        try {
            emailController.sendEmail(sendEmailRequest);
        } catch (Exception e) {
            assert true;
        }
    }
}