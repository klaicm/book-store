package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.Book;
import com.task.book_store.model.shared.BookTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import java.util.UUID;

@Schema(
    name = "Book",
    description = "Book model. Contains informations about book of specific name and type. Only one combination of name and type can exist in database."
)
public record ApiBookResponse(
    @Schema(
        description = "Book Universal Unique Identifier in database",
        requiredMode = RequiredMode.REQUIRED
    )
    UUID uuid,

    @Schema(
        description = "Book name",
        requiredMode = RequiredMode.REQUIRED
    )
    String name,

    @Schema(
        description = "Book base price",
        requiredMode = RequiredMode.REQUIRED
    )
    Double price,

    @Schema(
        description = "Book Type",
        requiredMode = RequiredMode.REQUIRED
    )
    BookTypeEnum type,

    @Schema(
        description = "Quantity of books available for purchase",
        requiredMode = RequiredMode.REQUIRED
    )
    Integer quantity
) {

  public static ApiBookResponse from(Book book) {
    return new ApiBookResponse(
        book.uuid(),
        book.name(),
        book.price(),
        book.type(),
        book.quantity()
    );
  }

  public static List<ApiBookResponse> from(List<Book> books) {
    return books.stream().map(ApiBookResponse::from).toList();
  }

}
