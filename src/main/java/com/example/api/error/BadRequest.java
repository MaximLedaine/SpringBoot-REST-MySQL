package com.example.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequest extends ResponseStatusException {
  static final long serialVersionUID = 10039;
  public BadRequest(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
