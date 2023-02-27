package com.task.book_store.model.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.UUID;

@Schema(
    name = "CustomerRequest",
    description = "Model containing information about specific customer"
)
public record ApiCustomerRequest(
    @Schema(
        description = "Customer Universal Unique Identifier in database",
        requiredMode = RequiredMode.REQUIRED,
        example = "479a1ca9-b7ed-46b5-ad94-2775f1321426"
    )
    UUID uuid
) {

}
