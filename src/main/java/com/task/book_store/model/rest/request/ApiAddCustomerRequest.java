package com.task.book_store.model.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(
    name = "AddCustomerRequest",
    description = "Model for adding new customer in database"
)
public record ApiAddCustomerRequest(
    @Schema(
        description = "Customer first name",
        requiredMode = RequiredMode.REQUIRED,
        example = "John"
    )
    String firstName,

    @Schema(
        description = "Customer last name",
        requiredMode = RequiredMode.REQUIRED,
        example = "Doe"
    )
    String lastName
) {

}
