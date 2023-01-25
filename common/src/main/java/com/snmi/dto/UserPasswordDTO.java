package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the user password manipulation functionality")
public class UserPasswordDTO {

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Password must not be null")
    @Schema(name = "The new password of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
