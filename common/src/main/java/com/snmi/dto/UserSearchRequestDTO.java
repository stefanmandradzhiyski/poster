package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.SortDirection;
import com.snmi.enums.SortUserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The class is used as a request in the user search functionality")
public class UserSearchRequestDTO {

    @Schema(name = "Will filter the users by the username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String username;

    @Schema(name = "Will sort the response by property: USERNAME, POST_COUNT", defaultValue = "USERNAME", requiredMode = Schema.RequiredMode.REQUIRED)
    private SortUserType sortType = SortUserType.USERNAME;

    @Schema(name = "Will sort the response in specific direction: ASC, DESC", defaultValue = "ASC", requiredMode = Schema.RequiredMode.REQUIRED)
    private SortDirection sortDirection = SortDirection.ASC;

}
