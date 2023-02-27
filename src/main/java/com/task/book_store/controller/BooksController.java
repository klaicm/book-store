package com.task.book_store.controller;

import com.task.book_store.exception.BookAlreadyExistsException;
import com.task.book_store.exception.BookNotFoundException;
import com.task.book_store.model.rest.request.ApiBookRequest;
import com.task.book_store.model.rest.response.ApiBookResponse;
import com.task.book_store.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Tag(
    name = "BooksController",
    description = "Enables users to view information about book inventory and add new books to the database"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {

  private final BookService bookService;

  @Operation(summary = "Method for fetching all books and their quantities in inventory")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApiBookResponse> getBooks() {
    return ApiBookResponse.from(bookService.getAllBooks());
  }

  @Operation(summary = "Method for fetching specific book from inventory")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(schema = @Schema(implementation = BookNotFoundException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(value = "/{uuid}")
  public ApiBookResponse getBook(@PathVariable UUID uuid) {
    return ApiBookResponse.from(bookService.getBookByUuid(uuid));
  }

  @Operation(summary = "Method for adding new book into the book store inventory")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = BookAlreadyExistsException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiBookResponse saveBook(@RequestBody ApiBookRequest request) {
    return ApiBookResponse.from(bookService.saveBook(request));
  }
}
