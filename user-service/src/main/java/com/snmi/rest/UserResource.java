package com.snmi.rest;

import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.UserActiveDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserPasswordDTO;
import com.snmi.dto.UserSearchRequestDTO;
import com.snmi.service.UserService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users API")
@RequestMapping("api/v1/users")
public class UserResource extends BaseControllerAdvice {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User has been updated successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public BaseResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return BaseResponse.build(userService.createUser(userDTO));
    }

    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user's password", responses = {
            @ApiResponse(responseCode = "200", description = "User's password has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "User doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<UserDTO> patchUserPassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        return BaseResponse.build(userService.patchUserPassword(userPasswordDTO));
    }

    @PatchMapping("/active")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user's active flag", responses = {
            @ApiResponse(responseCode = "200", description = "User's active flag has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "404", description = "User doesn't exist"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<UserDTO> patchUserActiveFlag(@Valid @RequestBody UserActiveDTO userActiveDTO) {
        return BaseResponse.build(userService.patchUserActiveFlag(userActiveDTO));
    }

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a specific user", responses = {
            @ApiResponse(responseCode = "200", description = "User has been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "404", description = "User doesn't exist"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<UserDTO> getUserByUsername(@PathVariable("username") final String username) {
        return BaseResponse.build(userService.getUserByUsername(username));
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a specific post", responses = {
            @ApiResponse(responseCode = "200", description = "User has been deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to update this entity"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<HttpStatus> deleteUser(@PathVariable("username") final String username) {
        userService.deleteUser(username);
        return BaseResponse.build(HttpStatus.OK);
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search for users", responses = {
            @ApiResponse(responseCode = "200", description = "Users have been found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<UserDTO>> searchUsers(@Valid @RequestBody final UserSearchRequestDTO searchRequestDTO) {
        return BaseResponse.build(userService.searchUsers(searchRequestDTO));
    }

}
