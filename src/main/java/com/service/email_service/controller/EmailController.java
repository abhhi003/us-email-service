package com.service.email_service.controller;

import com.service.email_service.service.EmailService;
import com.service.openapi.api.SendApi;
import com.service.openapi.model.SendEmail200Response;
import com.service.openapi.model.SendEmailRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("${microservice.context.path}")
public class EmailController implements SendApi {

    @Autowired
    EmailService emailService;

    Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Override
    public ResponseEntity<SendEmail200Response> sendEmail(@Valid @RequestBody SendEmailRequest sendEmailRequest) {
        LOG.debug("SendEmail Controller Start {}", sendEmailRequest);
        emailService.sendEmail(sendEmailRequest.getEmailId(), sendEmailRequest.getSubject(), sendEmailRequest.getMessage());
        LOG.debug("SendEmail Controller End");
        SendEmail200Response response = new SendEmail200Response();
        response.setMessage("Email sent successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
