package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the post display flag manipulation functionality")
public class PostDisplayDTO {

    @NotNull(message = "Id must not be null")
    @Size(min = 36, max = 36, message = "Id must be exactly 36 chars")
    @Schema(name = "The post ID that will be updated", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(name = "The display flag of the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean display;

}
