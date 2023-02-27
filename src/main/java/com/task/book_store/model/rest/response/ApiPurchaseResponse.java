package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Schema(
    name = "Purchase",
    description = "History information about specific purchase"
)
public record ApiPurchaseResponse(
    @Schema(
        description = "Purchase Universal Unique Identifier in database",
        requiredMode = RequiredMode.REQUIRED
    )
    UUID uuid,

    @Schema(
        description = "Purchase timestamp",
        requiredMode = RequiredMode.REQUIRED
    )
    OffsetDateTime createdAt,

    @Schema(
        description = "Actual information about customer that made this purchase",
        requiredMode = RequiredMode.REQUIRED
    )
    ApiCustomerResponse customer,

    @Schema(
        description = "List of purchased books, amount of each book with actual book state in inventory. It shows book data in the moment of purchase.",
        requiredMode = RequiredMode.REQUIRED
    )
    List<ApiPurchasedBookResponse> purchasedBooks,

    @Schema(
        description = "Total amount of purchase after all discounts and loyalty points are calculated.",
        requiredMode = RequiredMode.REQUIRED
    )
    Double priceTotal
) {

  public static ApiPurchaseResponse from(Purchase purchase) {
    return new ApiPurchaseResponse(
        purchase.uuid(),
        purchase.createdAt(),
        ApiCustomerResponse.from(purchase.customer()),
        ApiPurchasedBookResponse.from(purchase.purchasedBooks()),
        purchase.purchasedBooks().stream().mapToDouble(pb -> pb.finalPrice() * pb.quantity()).sum()
    );
  }

  public static List<ApiPurchaseResponse> from(List<Purchase> purchases) {
    return purchases.stream().map(ApiPurchaseResponse::from).toList();
  }

}
