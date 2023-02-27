package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.PurchasedBook;
import com.task.book_store.model.shared.BookTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(
    name = "PurchasedBook",
    description = "History information about purchased book. Contains information about the book in the moment of purchase."
)
public record ApiPurchasedBookResponse(
    @Schema(
        description = "Book that was purchased with current information. This data is currently active in inventory.",
        requiredMode = RequiredMode.REQUIRED
    )
    ApiBookResponse book,

    @Schema(
        description = "Book type in the moment of purchase",
        requiredMode = RequiredMode.REQUIRED
    )
    BookTypeEnum type,

    @Schema(
        description = "Base price of the book in the moment of purchase.",
        requiredMode = RequiredMode.REQUIRED
    )
    Double originalPrice,

    @Schema(
        description = "Final price of the book in the moment of purchase after all discounts and loyalty points are calculated.",
        requiredMode = RequiredMode.REQUIRED
    )
    Double finalPrice,

    @Schema(
        description = "Quantity purchased",
        requiredMode = RequiredMode.REQUIRED
    )
    Integer quantity
) {

  public static ApiPurchasedBookResponse from(PurchasedBook purchasedBook) {
    return new ApiPurchasedBookResponse(
        ApiBookResponse.from(purchasedBook.book()),
        purchasedBook.type(),
        purchasedBook.originalPrice(),
        purchasedBook.finalPrice(),
        purchasedBook.quantity()
    );
  }

  public static List<ApiPurchasedBookResponse> from(List<PurchasedBook> purchasedBooks) {
    return purchasedBooks.stream().map(ApiPurchasedBookResponse::from).toList();
  }
}
