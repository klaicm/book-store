package com.task.book_store.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class BookNotFoundException extends ResponseStatusException {

  public BookNotFoundException() {
    super(HttpStatus.NOT_FOUND, "Book not found");
    log.error("Book not found");
  }

  public BookNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
    log.error(message);
  }
}
