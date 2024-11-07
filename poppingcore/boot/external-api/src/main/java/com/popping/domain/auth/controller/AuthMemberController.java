package com.popping.domain.auth.controller;

import com.popping.domain.auth.dto.AuthMemberDto;
import com.popping.domain.auth.service.SignInService;
import com.popping.domain.auth.service.SignUpService;
import com.popping.global.responseform.ResponseForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseForm<AuthMemberDto.SignUpResponse>> signUp(@RequestPart @Valid AuthMemberDto.SignUpRequest signUpRequestDto,
                                                                             @RequestPart(required = false) MultipartFile img) {
        return ResponseEntity.created(URI.create(""))
                .body(ResponseForm.<AuthMemberDto.SignUpResponse>builder()
                        .httpStatus(HttpStatus.CREATED)
                        .responseMessage("Sign up successfully")
                        .content(signUpService.signUp(signUpRequestDto, img))
                        .build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseForm<AuthMemberDto.SignInResponse>> signIn(@RequestBody @Valid AuthMemberDto.SignInRequest signInRequestDto) {
        return ResponseEntity.ok(
                ResponseForm.<AuthMemberDto.SignInResponse>builder()
                        .httpStatus(HttpStatus.OK)
                        .responseMessage("Response successfully")
                        .content(signInService.signIn(signInRequestDto))
                        .build()
        );
    }

    @GetMapping("/sign-in/re/status")
    public ResponseEntity<ResponseForm<AuthMemberDto.SignInRequiredResponse>> verifySignInRequired(HttpServletRequest request) {
        return ResponseEntity.ok(
                ResponseForm.<AuthMemberDto.SignInRequiredResponse>builder()
                        .httpStatus(HttpStatus.OK)
                        .responseMessage("Retrieve successfully")
                        .content(signInService.verifySignInRequired(request.getHeader(AUTHORIZATION)))
                        .build()
        );
    }
}