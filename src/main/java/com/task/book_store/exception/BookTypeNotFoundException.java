package com.task.book_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookTypeNotFoundException extends ResponseStatusException {

  public BookTypeNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Book type not found");
  }
}
