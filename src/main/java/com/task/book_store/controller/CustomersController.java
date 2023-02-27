package com.task.book_store.controller;

import com.task.book_store.exception.CustomerNotFoundException;
import com.task.book_store.model.rest.request.ApiAddCustomerRequest;
import com.task.book_store.model.rest.response.ApiCustomerPurchasesResponse;
import com.task.book_store.model.rest.response.ApiCustomerResponse;
import com.task.book_store.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Tag(
    name = "CustomersController",
    description = "Enables users to view information about customers and to add new customer"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomersController {

  private final CustomerService customerService;

  @Operation(summary = "Method for fetching all customers and their information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApiCustomerResponse> getCustomers() {
    return ApiCustomerResponse.from(customerService.getCustomers());
  }

  @Operation(summary = "Method for fetching specific customer and to see its basic information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = CustomerNotFoundException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiCustomerResponse getCustomer(@PathVariable UUID uuid) {
    return ApiCustomerResponse.from(customerService.getCustomer(uuid));
  }

  @Operation(summary = "Method for fetching specific customer and to see all of his purchases")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = CustomerNotFoundException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(value = "/{uuid}/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiCustomerPurchasesResponse getCustomerPurchases(@PathVariable UUID uuid) {
    return ApiCustomerPurchasesResponse.from(customerService.getCustomerPurchases(uuid));
  }

  @Operation(summary = "Method for adding new customer in database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiCustomerResponse addCustomer(@RequestBody ApiAddCustomerRequest request) {
    return ApiCustomerResponse.from(customerService.addCustomer(request));
  }

  @Operation(summary = "Search customer by first or last name. You can enter only part of the name.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(implementation = BadRequest.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = RuntimeException.class))})
  })
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApiCustomerResponse> findCustomers(
      @Parameter(
          description = "Term for which customer is searched. In this scope it only includes first or last name. You can give it just part of the string.",
          required = true,
          example = "Jaz"
      )
      @RequestParam String searchTerm
  ) {
    return ApiCustomerResponse.from(customerService.findCustomer(searchTerm));
  }

}
