package com.task.book_store.model.rest.request;

import com.task.book_store.model.shared.BookTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(
    name = "BookRequest",
    description = "Model for adding new book in inventory"
)
public record ApiBookRequest(
    @Schema(
        description = "Book name",
        requiredMode = RequiredMode.REQUIRED,
        example = "The Great Gatsby"
    )
    String name,

    @Schema(
        description = "Quantity of books that will be added in inventory",
        requiredMode = RequiredMode.REQUIRED,
        example = "15"
    )
    Integer quantity,

    @Schema(
        description = "Book type",
        requiredMode = RequiredMode.REQUIRED
    )
    BookTypeEnum bookTypeEnum,

    @Schema(
        description = "Base book price",
        requiredMode = RequiredMode.REQUIRED,
        example = "15.99"
    )
    Double price
) {

}
