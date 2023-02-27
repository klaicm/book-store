package com.task.book_store.controller;

import com.task.book_store.model.rest.response.ApiBookTypeResponse;
import com.task.book_store.service.BookTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "BookTypesController",
    description = "Provides information about book types"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/book-types")
public class BookTypesController {

  private final BookTypeService bookTypeService;

  @Operation(summary = "Method for fetching all book types")

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApiBookTypeResponse> getBookTypes() {
    return ApiBookTypeResponse.from(bookTypeService.getBookTypes());
  }
}
