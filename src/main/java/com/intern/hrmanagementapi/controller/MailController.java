package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {EndpointConst.Mail.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Mail", description = "The mail API")
public class MailController {

  @Autowired
  private final MailService mailService;


  @Operation(summary = "Send email", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping(value = {EndpointConst.Mail.SEND}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> sendWithAttachment(@RequestParam String[] emailAddress,
      @RequestParam(name = "subject") String subject, @RequestParam(name = "text") String text,
      @RequestPart(name = "files", required = false) @RequestParam(name = "files", required = false) List<MultipartFile> files)
      throws MessagingException, IOException {

    var res = mailService.sendEmailWithFile(emailAddress, subject, text, files);

    return ResponseEntity.ok(res);

  }
}
