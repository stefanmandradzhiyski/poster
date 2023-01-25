package com.snmi.rest;

import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.BatchPostDisplayDTO;
import com.snmi.dto.CommentDTO;
import com.snmi.dto.LikeDTO;
import com.snmi.dto.PostDTO;
import com.snmi.dto.PostDisplayDTO;
import com.snmi.dto.PostSearchRequestDTO;
import com.snmi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Posts API")
@RequestMapping("api/v1/posts")
public class PostResource extends BaseControllerAdvice {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new post", responses = {
            @ApiResponse(responseCode = "200", description = "Post has been created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return BaseResponse.build(postService.createPost(postDTO));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a specific post", responses = {
            @ApiResponse(responseCode = "200", description = "Post has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) {
        return BaseResponse.build(postService.updatePost(postDTO));
    }

    @PatchMapping("/display")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change specific post display flag", responses = {
            @ApiResponse(responseCode = "200", description = "Post display flag has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<PostDTO> patchPost(@Valid @RequestBody PostDisplayDTO postDisplayDTO) {
        return BaseResponse.build(postService.patchPostDisplay(postDisplayDTO));
    }

    @PatchMapping("/displays")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update several posts display flags", responses = {
            @ApiResponse(responseCode = "200", description = "Posts display flags have been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update those entities"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<PostDTO>> patchPosts(@Valid @RequestBody BatchPostDisplayDTO batchPostDisplayDTO) {
        return BaseResponse.build(postService.patchPostsDisplays(batchPostDisplayDTO));
    }

    @GetMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a specific post", responses = {
            @ApiResponse(responseCode = "200", description = "Post has been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<PostDTO> getPost(@PathVariable("postId") final String postId) {
        return BaseResponse.build(postService.getPostById(postId));
    }

    @DeleteMapping("/specific/{postId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a specific post", responses = {
            @ApiResponse(responseCode = "200", description = "Post has been deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<HttpStatus> deletePost(@PathVariable("postId") final String postId) {
        postService.deletePost(postId);
        return BaseResponse.build(HttpStatus.OK);
    }

    @DeleteMapping("/several/{username}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete all user's posts", responses = {
            @ApiResponse(responseCode = "200", description = "All the posts have been deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<HttpStatus> deletePostByUsername(@PathVariable("username") final String username) {
        postService.deleteByUsername(username);
        return BaseResponse.build(HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Find all post's comments", responses = {
            @ApiResponse(responseCode = "200", description = "Comments have been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<CommentDTO>> findAllPostComments(@PathVariable("postId") final String postId) {
        return BaseResponse.build(postService.findAllPostComments(postId));
    }

    @GetMapping("/{postId}/likes")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Find all post's likes", responses = {
            @ApiResponse(responseCode = "200", description = "Likes have been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "Post doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<LikeDTO>> findAllPostLikes(@PathVariable("postId") final String postId) {
        return BaseResponse.build(postService.findAllPostLikes(postId));
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search for posts", responses = {
            @ApiResponse(responseCode = "200", description = "Posts have been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<PostDTO>> searchPosts(@Valid @RequestBody final PostSearchRequestDTO searchRequestDTO) {
        return BaseResponse.build(postService.searchPosts(searchRequestDTO));
    }

}
