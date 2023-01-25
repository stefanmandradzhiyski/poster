package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the user display flag manipulation functionality")
public class UserActiveDTO {

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(name = "The display flag of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean active;

}
