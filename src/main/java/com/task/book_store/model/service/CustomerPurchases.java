package com.task.book_store.model.service;

import com.task.book_store.model.db.DbCustomer;
import java.util.List;

public record CustomerPurchases(
    Customer customer,
    List<Purchase> purchases
) {

  public static CustomerPurchases from(DbCustomer dbCustomer) {
    return new CustomerPurchases(
        Customer.from(dbCustomer),
        Purchase.from(dbCustomer.getPurchases().stream().toList())
    );
  }
}
