package com.task.book_store.model.service;

import com.task.book_store.model.db.DbCustomer;
import java.util.List;
import java.util.UUID;

public record Customer(
    UUID uuid,
    String firstName,
    String lastName,
    Integer loyaltyPoints
) {

  public static Customer from(DbCustomer dbCustomer) {
    return new Customer(
        dbCustomer.getUuid(),
        dbCustomer.getFirstName(),
        dbCustomer.getLastName(),
        dbCustomer.getLoyaltyPoints()
    );
  }

  public static List<Customer> from(List<DbCustomer> dbCustomers) {
    return dbCustomers.stream().map(Customer::from).toList();
  }

}
