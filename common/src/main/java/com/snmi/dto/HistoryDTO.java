package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.HistoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a main presentation of a specific interaction in all the functionalities")
public class HistoryDTO {

    @Schema(name = "The id of the specific interaction", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotNull(message = "Post id must not be null")
    @Size(min = 36, max = 36, message = "Post id must be exactly 36 chars")
    @Schema(name = "The post that is related to the interaction", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postId;

    @NotNull(message = "Post title must not be null")
    @Size(max = 128, message = "Post title must not be longer than 128 chars")
    @Schema(name = "The post title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postTitle;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user that interacted with the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Interaction type must not be null")
    @Schema(name = "The type of the interaction", requiredMode = Schema.RequiredMode.REQUIRED)
    private HistoryType type;

    @Schema(name = "The date when the interaction has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the interaction has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

}
