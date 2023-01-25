package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a main presentation of a specific user in all the functionalities")
public class UserDTO {

    @Schema(name = "The id of the specific user", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 chars")
    @Schema(name = "The password of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(name = "The visible post count of the user", accessMode = Schema.AccessMode.READ_ONLY)
    private int visiblePostCount;

    @Schema(name = "The total post count of the user", accessMode = Schema.AccessMode.READ_ONLY)
    private int totalPostCount;

    @Schema(name = "The active flag of the user", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;

    @Schema(name = "The date when the user has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the user has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

}
