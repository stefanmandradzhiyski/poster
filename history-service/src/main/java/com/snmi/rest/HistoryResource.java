package com.snmi.rest;

import com.snmi.controller.BaseControllerAdvice;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.HistoryDTO;
import com.snmi.enums.HistoryType;
import com.snmi.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "History API")
@RequestMapping("api/v1/history")
public class HistoryResource extends BaseControllerAdvice {

    private final HistoryService historyService;

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Find user's history", responses = {
            @ApiResponse(responseCode = "200", description = "History has been updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "403", description = "No access to manipulate this entity"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    }, security = @SecurityRequirement(name = "bearer-key"))
    public BaseResponse<List<HistoryDTO>> findByUsernameAndType(
        @PathVariable("username") final String username,
        @RequestParam("type") final HistoryType type
    ) {
        return BaseResponse.build(historyService.findByUsernameAndType(username, type));
    }

}
