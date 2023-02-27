package com.task.book_store.model.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(
    name = "PurchaseRequest",
    description = "Model used when making new purchase. Contains information about customer and purchased books."
)
public record ApiPurchaseRequest(
    @Schema(
        description = "Customer that is making purchase",
        requiredMode = RequiredMode.REQUIRED
    )
    ApiCustomerRequest customer,

    @Schema(
        description = "List of purchased books",
        requiredMode = RequiredMode.REQUIRED
    )
    List<ApiPurchasedBookRequest> purchasedBooks
) {

}
