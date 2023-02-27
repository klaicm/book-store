package com.task.book_store.model.service;

import com.task.book_store.model.db.DbBook;
import com.task.book_store.model.shared.BookTypeEnum;
import java.util.List;
import java.util.UUID;

public record Book(
    UUID uuid,
    String name,
    Double price,
    BookTypeEnum type,
    Integer quantity
) {

  public static Book from(DbBook dbBook) {
    return new Book(
        dbBook.getUuid(),
        dbBook.getName(),
        dbBook.getPrice(),
        dbBook.getBookType().getType(),
        dbBook.getQuantity()
    );
  }

  public static List<Book> from(List<DbBook> dbBooks) {
    return dbBooks.stream().map(Book::from).toList();
  }

}
