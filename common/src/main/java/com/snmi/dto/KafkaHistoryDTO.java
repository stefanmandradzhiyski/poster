package com.snmi.dto;

import com.snmi.enums.Count;
import com.snmi.enums.HistoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The class is used by the Kafka's topic history.interaction")
public class KafkaHistoryDTO implements Serializable {

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user that interacted with the post", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Post id must not be null")
    @Size(min = 36, max = 36, message = "Post id must be exactly 36 chars")
    @Schema(name = "The post that is related to the interaction", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postId;

    @NotNull(message = "Post title must not be null")
    @Size(max = 128, message = "Post title must not be longer than 128 chars")
    @Schema(name = "The post title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postTitle;

    @NotNull(message = "Interaction type must not be null")
    @Schema(name = "The type of the interaction", requiredMode = Schema.RequiredMode.REQUIRED)
    private HistoryType type;

    @NotNull(message = "The action type")
    @Schema(name = "It is used to determinate if the interaction must be created or deleted", requiredMode = Schema.RequiredMode.REQUIRED)
    private Count action;

}
