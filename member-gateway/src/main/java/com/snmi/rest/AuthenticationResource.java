package com.snmi.rest;

import com.snmi.client.AuthenticationServiceClient;
import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.AuthenticationDTO;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.JwtToken;
import com.snmi.dto.UserProfileDTO;
import com.snmi.exception.UnAuthorizedException;
import com.snmi.service.DomainUserService;
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

import static com.snmi.constants.SecurityGlobalConstant.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication API")
public class AuthenticationResource extends BaseControllerAdvice {

    private final DomainUserService domainUserService;
    private final AuthenticationServiceClient authenticationServiceClient;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    @Operation(summary = "User log in", responses = {
            @ApiResponse(responseCode = "200", description = "User has been logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public BaseResponse<JwtToken> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        final BaseResponse<JwtToken> token = authenticationServiceClient.login(
                authenticationServiceClient.generateAnonymousToken(),
                authenticationDTO
        );

        if (token.getData() == null) {
            throw new UnAuthorizedException(UNAUTHORIZED);
        }

        UserProfileDTO user = domainUserService.activateUserProfile(
                token.getData().getToken(),
                authenticationDTO.getUsername()
        );

        if (user == null) {
            throw new UnAuthorizedException(UNAUTHORIZED);
        }

        return token;
    }

}
