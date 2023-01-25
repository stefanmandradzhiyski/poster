package com.snmi.rest;

import com.snmi.client.CommentServiceClient;
import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.CommentDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.snmi.constants.SecurityGlobalConstant.TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comments API")
@RequestMapping("api/v1/comments")
public class CommentResource extends BaseControllerAdvice {

    private final CommentServiceClient commentServiceClient;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment has been created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<CommentDTO> createComment(
        @RequestHeader(value = TOKEN_HEADER) String token,
        @Valid @RequestBody CommentDTO commentDTO
    ) {
        return commentServiceClient.createComment(token, commentDTO);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a specific comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "Comment or post don't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<CommentDTO> updateComment(
        @RequestHeader(value = TOKEN_HEADER) String token,
        @Valid @RequestBody CommentDTO commentDTO
    ) {
        return commentServiceClient.updateComment(token, commentDTO);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a specific comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment has been deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<HttpStatus> deleteComment(
        @RequestHeader(value = TOKEN_HEADER) String token,
        @PathVariable("commentId") final String commentId
    ) {
        return commentServiceClient.deleteComment(token, commentId);
    }

}
