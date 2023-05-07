package com.intern.hrmanagementapi.helper;

import com.intern.hrmanagementapi.exception.JwtException;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ObjectExceptionHandler {

  @ExceptionHandler(ObjectException.class)
  public ResponseEntity<?> handleObjectException(ObjectException ex) {
    return ResponseEntity.badRequest().body(
        DataResponseDto.error(ex.getHttpStatus().value(), ex.getHttpStatus().name(),
            ex.getError()));
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<?> handleJwtException(JwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  @ExceptionHandler(value = {ExpiredJwtException.class})
  public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
    String message = "Token expired heheeh";
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
  }
}
