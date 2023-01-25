package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used to represents the token")
public class JwtToken {

    @Schema(name = "The token that the user will use in the platform", accessMode = Schema.AccessMode.READ_ONLY)
    private String token;

}
