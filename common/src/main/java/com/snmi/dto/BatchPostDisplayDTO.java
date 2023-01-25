package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the post display flag manipulation functionality")
public class BatchPostDisplayDTO {

    @NotNull(message = "The set must not be null")
    @NotEmpty(message = "The set must not be empty")
    @Schema(name = "A set of different post display requests", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<@Valid PostDisplayDTO> requests;

}
