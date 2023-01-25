package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a main presentation of a specific comment in all the functionalities")
public class CommentDTO {

    @Schema(name = "The id of the comment", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotNull(message = "Post id must not be null")
    @Size(min = 36, max = 36, message = "Post id must be exactly 36 chars")
    @Schema(name = "The post that is related to the comment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postId;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user that created the comment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Content must not be null")
    @Size(min = 5, max = 255, message = "Content must be between 5 and 255 chars")
    @Schema(name = "The content of the comment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Min(0)
    @Max(5)
    @Schema(name = "The rating that the user left", requiredMode = Schema.RequiredMode.REQUIRED)
    private int rating;

    @Schema(name = "The date when the comment has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the comment has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

}
