package com.snmi.rest;

import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.AuthenticationDTO;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.JwtToken;
import com.snmi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication API")
public class AuthenticationResource extends BaseControllerAdvice {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    @Operation(summary = "User log in", responses = {
            @ApiResponse(responseCode = "200", description = "User has been logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public BaseResponse<JwtToken> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        return BaseResponse.build(authenticationService.generateJWTToken(
            authenticationDTO.getUsername(),
            authenticationDTO.getPassword()
        ));
    }

}
