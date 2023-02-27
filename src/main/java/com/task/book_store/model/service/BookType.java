package com.task.book_store.model.service;

import com.task.book_store.model.db.DbBookType;
import com.task.book_store.model.shared.BookTypeEnum;
import java.util.List;

public record BookType(
    BookTypeEnum type,
    Double discount
) {

  public static BookType from(DbBookType dbBookType) {
    return new BookType(
        dbBookType.getType(),
        dbBookType.getDiscount()
    );
  }

  public static List<BookType> from(List<DbBookType> dbBookTypes) {
    return dbBookTypes.stream().map(BookType::from).toList();
  }

}
