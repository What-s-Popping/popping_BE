package com.popping.domain.auth.controller;

import com.popping.domain.auth.dto.TokenDto;
import com.popping.domain.auth.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    private static final String AUTHORIZATION = "Authorization";

    @GetMapping("/tokens")
    public ResponseEntity<TokenDto.TokenResponse> reIssueTokens(HttpServletRequest request) {
        return ResponseEntity.ok(tokenService.reIssueTokens(request.getHeader(AUTHORIZATION)));
    }
}
