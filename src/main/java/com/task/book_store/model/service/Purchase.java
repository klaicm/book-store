package com.task.book_store.model.service;

import com.task.book_store.model.db.DbPurchase;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Purchase(
    UUID uuid,
    OffsetDateTime createdAt,
    Customer customer,
    List<PurchasedBook> purchasedBooks
) {

  public static Purchase from(DbPurchase dbPurchase) {
    return new Purchase(
        dbPurchase.getUuid(),
        dbPurchase.getCreatedAt(),
        Customer.from(dbPurchase.getCustomer()),
        PurchasedBook.from(dbPurchase.getPurchasedBooks())
    );
  }

  public static List<Purchase> from(List<DbPurchase> dbPurchases) {
    return dbPurchases.stream().map(Purchase::from).toList();
  }
}
