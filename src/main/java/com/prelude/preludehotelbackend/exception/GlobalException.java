package com.prelude.preludehotelbackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalException {
@ExceptionHandler(UsernameNotFoundException.class)
@Nullable
public ResponseEntity<Object> UsernameNotFoundException(
        NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Resource not found");
    body.put("details", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
}
}
