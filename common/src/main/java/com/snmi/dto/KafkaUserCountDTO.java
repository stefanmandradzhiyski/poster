package com.snmi.dto;

import com.snmi.enums.Count;
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
@Schema(description = "The class is used by the Kafka's topic user.post.count")
public class KafkaUserCountDTO implements Serializable {

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    @Schema(name = "The username of the user which count must be changed", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull(message = "Visible post action must not be null")
    @Schema(name = "Determines how the user visible post count has to be changed", requiredMode = Schema.RequiredMode.REQUIRED)
    private Count visiblePost;

    @NotNull(message = "Total post action must not be null")
    @Schema(name = "Determines how the user total post count has to be changed", requiredMode = Schema.RequiredMode.REQUIRED)
    private Count totalPost;

}
