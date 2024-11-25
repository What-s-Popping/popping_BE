package com.popping.domain.auth.controller;

import com.popping.domain.auth.dto.AuthSmsDto;
import com.popping.domain.auth.service.SmsVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {
    private final SmsVerifyService smsVerifyUseCase;

    @PostMapping("/send")
    public ResponseEntity<Void> sendSms() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthSmsDto.VerifyCodeResponse> verifySmsCode(@RequestBody @Valid AuthSmsDto.VerifyCodeRequest request) {
        return ResponseEntity.ok(smsVerifyUseCase.verifyCode(request));
    }
}
