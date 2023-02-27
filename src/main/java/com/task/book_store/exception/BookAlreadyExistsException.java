package com.task.book_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyExistsException extends ResponseStatusException {

  public BookAlreadyExistsException() {
    super(HttpStatus.CONFLICT, "Book with same name and type already exists");
  }
}
