package com.task.book_store.model.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.UUID;

@Schema(
    name = "PurchasedBookRequest",
    description = "Model containing information about specific book and quantity of purchase"
)
public record ApiPurchasedBookRequest(
    @Schema(
        description = "Book Universal Unique Identifier in database",
        requiredMode = RequiredMode.REQUIRED,
        example = "7c77c42a-11e8-4c43-822a-4deaf70854d0"
    )
    UUID uuid,

    @Schema(
        description = "Purchase quantity",
        requiredMode = RequiredMode.REQUIRED,
        example = "4"
    )
    Integer quantity
) {

}
