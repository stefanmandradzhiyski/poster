package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the login functionality")
public class AuthenticationDTO {

    @NotNull(message = "Username must not be null")
    @Schema(name = "The username of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Password must not be null")
    @Schema(name = "The password of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
