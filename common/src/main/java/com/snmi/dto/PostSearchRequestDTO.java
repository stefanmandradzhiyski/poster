package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.SortDirection;
import com.snmi.enums.SortPostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the post search functionality")
public class PostSearchRequestDTO {

    @Schema(name = "Will filter the posts by the title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(name = "Will filter the posts by the author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String username;

    @Schema(name = "Will filter the posts by the overall rating", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer minimumOverallRating;

    @Schema(name = "Will filter the posts by the display flag", defaultValue = "FALSE", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean inactiveIncluded;

    @Schema(name = "Will sort the response by property: RATING, CREATION_DATE", defaultValue = "CREATION_DATE", requiredMode = Schema.RequiredMode.REQUIRED)
    private SortPostType sortType = SortPostType.CREATION_DATE;

    @Schema(name = "Will sort the response in specific direction: ASC, DESC", defaultValue = "ASC", requiredMode = Schema.RequiredMode.REQUIRED)
    private SortDirection sortDirection = SortDirection.ASC;

}
