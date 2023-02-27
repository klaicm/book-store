package com.task.book_store.controller;

import com.task.book_store.exception.BadRequestException;
import com.task.book_store.exception.BookNotFoundException;
import com.task.book_store.exception.CustomerNotFoundException;
import com.task.book_store.exception.NotEnoughBooksException;
import com.task.book_store.model.rest.request.ApiPurchaseRequest;
import com.task.book_store.model.rest.response.ApiPurchaseResponse;
import com.task.book_store.service.PurchaseService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "PurchasesController",
    description = "Provides information about purchases and enables the creation of new purchases"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchasesController {

  private final PurchaseService purchaseService;

  @Operation(summary = "Method for fetching all purchases")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApiPurchaseResponse> getPurchases() {
    return ApiPurchaseResponse.from(purchaseService.getPurchases());
  }

  @Operation(summary = "Method used for creating new purchase")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequestException.class))}),
      @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = BookNotFoundException.class))}),
      @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = CustomerNotFoundException.class))}),
      @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = NotEnoughBooksException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @PostMapping
  public ApiPurchaseResponse purchase(@RequestBody ApiPurchaseRequest request) {
    return ApiPurchaseResponse.from(purchaseService.purchase(request));
  }
}
