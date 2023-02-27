package com.task.book_store.model.service;

import com.task.book_store.model.db.DbPurchasedBook;
import com.task.book_store.model.shared.BookTypeEnum;
import java.util.List;
import java.util.Set;

public record PurchasedBook(
    Book book,
    BookTypeEnum type,
    Double originalPrice,
    Double finalPrice,
    Integer quantity
) {

  public static PurchasedBook from(DbPurchasedBook dbPurchasedBook) {
    return new PurchasedBook(
        Book.from(dbPurchasedBook.getBook()),
        dbPurchasedBook.getType(),
        dbPurchasedBook.getSingleCopyOriginalPrice(),
        dbPurchasedBook.getSingleCopyFinalPrice(),
        dbPurchasedBook.getQuantity()
    );
  }

  public static List<PurchasedBook> from(Set<DbPurchasedBook> dbPurchasedBooks) {
    return dbPurchasedBooks.stream().map(PurchasedBook::from).toList();
  }

}
