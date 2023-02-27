package com.task.book_store.service;

import com.task.book_store.exception.BadRequestException;
import com.task.book_store.exception.CustomerNotFoundException;
import com.task.book_store.model.db.DbCustomer;
import com.task.book_store.model.rest.request.ApiAddCustomerRequest;
import com.task.book_store.model.service.Customer;
import com.task.book_store.model.service.CustomerPurchases;
import com.task.book_store.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
    CustomerRepository customerRepository
) {

  public List<Customer> getCustomers() {
    return Customer.from(customerRepository.findAll());
  }

  public DbCustomer getDbCustomer(UUID uuid) {
    return customerRepository.findByUuid(uuid).orElseThrow(CustomerNotFoundException::new);
  }

  public Customer getCustomer(UUID uuid) {
    return Customer.from(
        customerRepository
            .findByUuid(uuid)
            .orElseThrow(CustomerNotFoundException::new)
    );
  }

  public CustomerPurchases getCustomerPurchases(UUID uuid) {
    return CustomerPurchases.from(
        customerRepository
            .findByUuid(uuid)
            .orElseThrow(CustomerNotFoundException::new)
    );
  }

  public Customer addCustomer(ApiAddCustomerRequest request) {
    validateRequest(request);

    return Customer.from(
        customerRepository.save(
            new DbCustomer()
                .setUuid(UUID.randomUUID())
                .setFirstName(request.firstName())
                .setLastName(request.lastName())
                .setLoyaltyPoints(0)
        )
    );
  }

  public List<Customer> findCustomer(String searchTerm) {
    return Customer.from(
        customerRepository.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(searchTerm, searchTerm)
    );
  }

  private void validateRequest(ApiAddCustomerRequest request) {
    if (request.firstName() == null || request.firstName().isEmpty()) {
      throw new BadRequestException("Invalid first name");
    }

    if (request.lastName() == null || request.lastName().isEmpty()) {
      throw new BadRequestException("Invalid last name");
    }
  }
}
