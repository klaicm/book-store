package com.task.book_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerNotFoundException extends ResponseStatusException {

  public CustomerNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Customer not found");
  }

}
