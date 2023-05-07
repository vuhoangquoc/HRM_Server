package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.model.DataResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * File service.
 */
@Service
@RequiredArgsConstructor
public class MailService {

  private JavaMailSender javaMailSender;

  @Autowired
  public MailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public DataResponseDto sendEmailWithFile(String[] emailAddress, String subject, String text,
      List<MultipartFile> files) throws MailException, MessagingException, IOException {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

    helper.setFrom(new InternetAddress("trash.acc.279@gmail.com", "Devet HRM"));
    helper.setTo(emailAddress);
    helper.setSentDate(new Date());
    helper.setSubject(subject);
    helper.setText(text);
    if (files != null && files.size() != 0) {
//      response = DataResponseDto.error(HttpStatus.BAD_REQUEST.value(),
//          HttpStatus.BAD_REQUEST.name(), String.format("%s", MessageConst.File.UPLOAD_EMPTY));
//      return response;
      for (MultipartFile file : files) {

        helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()),
            new ByteArrayResource(file.getBytes()));
      }
    }
    javaMailSender.send(mimeMessage);
    return DataResponseDto.success(HttpStatus.OK.value(), "Your mail has been send.", null);
  }

}
