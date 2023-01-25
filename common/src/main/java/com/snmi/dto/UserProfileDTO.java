package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a main presentation of user's profile")
public class UserProfileDTO {

    @Schema(name = "The id of the user", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(name = "The username of the specific user", accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(name = "The visible post count of the specific user", accessMode = Schema.AccessMode.READ_ONLY)
    private int visiblePostCount;

    @Schema(name = "The total post count of the specific user", accessMode = Schema.AccessMode.READ_ONLY)
    private int totalPostCount;

    @Schema(name = "The active flag of the specific user", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean active;

    @Schema(name = "The date when the user has been created", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @Schema(name = "The date when the user has been updated", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

    @Schema(name = "A full list of user's posts", accessMode = Schema.AccessMode.READ_ONLY)
    private List<PostDTO> posts;

}
