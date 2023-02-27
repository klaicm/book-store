package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.CustomerPurchases;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(
    name = "CustomerPurchases",
    description = "Response model for obtaining information about customer purchases and details from that purchases."
)
public record ApiCustomerPurchasesResponse(
    @Schema(
        description = "Customer details",
        requiredMode = RequiredMode.REQUIRED
    )
    ApiCustomerResponse customer,

    @Schema(
        description = "List of all customer purchases",
        requiredMode = RequiredMode.REQUIRED
    )
    List<ApiPurchaseResponse> purchases
) {


  public static ApiCustomerPurchasesResponse from(CustomerPurchases customerPurchases) {
    return new ApiCustomerPurchasesResponse(
        ApiCustomerResponse.from(customerPurchases.customer()),
        ApiPurchaseResponse.from(customerPurchases.purchases())
    );
  }
}
