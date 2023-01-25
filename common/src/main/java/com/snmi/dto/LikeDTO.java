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
@Schema(description = "The class is used as a main presentation of a specific like in all the functionalities")
public class LikeDTO {

    @Schema(name = "The id of the specific like", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotNull(message = "Post id must not be null")
    @Size(min = 36, max = 36, message = "Post id must be exactly 36 chars")
    @Schema(name = "The post that is related to the like", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postId;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user that liked the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(name = "The date when the like has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the like has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

}
