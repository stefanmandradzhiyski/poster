package com.snmi.rest;

import com.snmi.client.LikeServiceClient;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.LikeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.snmi.constants.SecurityGlobalConstant.TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@Tag(name = "Likes API")
@RequestMapping("api/v1/likes")
public class LikesResource {

    private final LikeServiceClient likeServiceClient;

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new like", responses = {
            @ApiResponse(responseCode = "200", description = "Like has been created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<LikeDTO> createLike(
            @RequestHeader(value = TOKEN_HEADER) String token,
            @Valid @RequestBody LikeDTO likeDTO
    ) {
        return likeServiceClient.createLike(token, likeDTO);
    }

    @DeleteMapping("/{likeId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a specific like", responses = {
            @ApiResponse(responseCode = "200", description = "Like has been deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<HttpStatus> deleteLike(
            @RequestHeader(value = TOKEN_HEADER) String token,
            @PathVariable("likeId") final String likeId
    ) {
        return likeServiceClient.deleteLike(token, likeId);
    }

}
