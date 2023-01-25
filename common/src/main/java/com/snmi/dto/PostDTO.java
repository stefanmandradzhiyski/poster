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
@Schema(description = "The class is used as a main presentation of a specific post in all the functionalities")
public class PostDTO {

    @Schema(name = "The id of the specific post", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user that created the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Post title must not be null")
    @Size(max = 128, message = "Post title must not be longer than 128 chars")
    @Schema(name = "The post title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Size(max = 512, message = "Short description must not be longer than 512 chars")
    @Schema(name = "The short description of the post", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String shortDescription;

    @NotNull(message = "Content must not be null")
    @Schema(name = "The full content of the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(name = "The count of all post's views", accessMode = Schema.AccessMode.READ_ONLY)
    private int viewCount;

    @Schema(name = "The count of all post's likes", accessMode = Schema.AccessMode.READ_ONLY)
    private int likeCount;

    @Schema(name = "The count of all post's comment", accessMode = Schema.AccessMode.READ_ONLY)
    private int commentCount;

    @Schema(name = "The total rating points collected for a specific post", accessMode = Schema.AccessMode.READ_ONLY)
    private int totalRatingPoints;

    @Schema(name = "The overall rating of the post", accessMode = Schema.AccessMode.READ_ONLY)
    private double overallRating;

    @Schema(name = "The display flag of the post", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean display;

    @Schema(name = "The date when the post has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the post has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

}
