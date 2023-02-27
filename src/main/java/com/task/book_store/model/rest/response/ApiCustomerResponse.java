package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import java.util.UUID;

@Schema(
    name = "Customer",
    description = "Customer information including amount of loyalty points"
)
public record ApiCustomerResponse(
    @Schema(
        description = "Customer Universal Unique Identifier in database",
        requiredMode = RequiredMode.REQUIRED
    )
    UUID uuid,

    @Schema(
        description = "Customer first name",
        requiredMode = RequiredMode.REQUIRED
    )
    String firstName,

    @Schema(
        description = "Customer last name",
        requiredMode = RequiredMode.REQUIRED
    )
    String lastName,

    @Schema(
        description = "Amount of customer loyalty points",
        requiredMode = RequiredMode.REQUIRED
    )
    Integer loyaltyPoints
) {

  public static ApiCustomerResponse from(Customer customer) {
    return new ApiCustomerResponse(
        customer.uuid(),
        customer.firstName(),
        customer.lastName(),
        customer.loyaltyPoints()
    );
  }

  public static List<ApiCustomerResponse> from(List<Customer> customers) {
    return customers.stream().map(ApiCustomerResponse::from).toList();
  }
}
