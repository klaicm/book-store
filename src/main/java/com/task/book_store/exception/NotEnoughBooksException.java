package com.task.book_store.exception;

public class NotEnoughBooksException extends RuntimeException {

  public NotEnoughBooksException(String message) {
    super(message);
  }
}
