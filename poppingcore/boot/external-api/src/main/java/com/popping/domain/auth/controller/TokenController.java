package com.popping.domain.auth.controller;

import com.popping.domain.auth.dto.TokenDto;
import com.popping.domain.auth.service.TokenService;
import com.popping.global.responseform.ResponseForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    private static final String AUTHORIZATION = "Authorization";

    @GetMapping("/tokens")
    public ResponseEntity<ResponseForm<?>> reIssueTokens(HttpServletRequest request) {
        return ResponseEntity.ok(
                ResponseForm.<TokenDto.TokenResponse>builder()
                        .httpStatus(HttpStatus.OK)
                        .responseMessage("Retrieve successfully")
                        .content(tokenService.reIssueTokens(request.getHeader(AUTHORIZATION)))
                        .build()
        );
    }
}
