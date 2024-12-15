package com.popping.domain.auth.controller;

import com.popping.domain.auth.dto.AuthMemberDto;
import com.popping.domain.auth.service.SignInService;
import com.popping.domain.auth.service.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthMemberController {
    private final SignUpService signUpService;
    private final SignInService signInService;

    private static final String AUTHORIZATION = "Authorization";

    @PostMapping("/sign-up")
    public ResponseEntity<AuthMemberDto.SignUpResponse> signUp(@RequestParam @Valid AuthMemberDto.SignUpRequest signUpRequestDto) {
        return ResponseEntity.created(URI.create(""))
                .body(signUpService.signUp(signUpRequestDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthMemberDto.SignInResponse> signIn(@RequestBody @Valid AuthMemberDto.SignInRequest signInRequestDto) {
        return ResponseEntity.ok(signInService.signIn(signInRequestDto));
    }

    @GetMapping("/sign-in/re/status")
    public ResponseEntity<AuthMemberDto.SignInRequiredResponse> verifySignInRequired(HttpServletRequest request) {
        return ResponseEntity.ok(signInService.verifySignInRequired(request.getHeader(AUTHORIZATION)));
    }
}