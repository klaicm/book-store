package com.task.book_store.model.rest.response;

import com.task.book_store.model.service.BookType;
import com.task.book_store.model.shared.BookTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(
    name = "BookType",
    description = "Book type. Describes current state of book and determines discount amount for specific type."
)
public record ApiBookTypeResponse(
    @Schema(
        description = "Type of the book. Enumerated.",
        requiredMode = RequiredMode.REQUIRED
    )
    BookTypeEnum type,

    @Schema(
        description = "Discount amount for specific book type.",
        requiredMode = RequiredMode.REQUIRED
    )
    Double discount
) {

  public static ApiBookTypeResponse from(BookType bookType) {
    return new ApiBookTypeResponse(
        bookType.type(),
        bookType.discount()
    );
  }

  public static List<ApiBookTypeResponse> from(List<BookType> bookTypes) {
    return bookTypes.stream().map(ApiBookTypeResponse::from).toList();
  }

}
