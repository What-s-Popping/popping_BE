package com.popping.domain.auth.service;

import com.popping.data.member.data.member.entity.Member;
import com.popping.data.member.data.member.service.MemberService;
import com.popping.domain.auth.dto.AuthSmsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmsVerifyService {
    private final MemberService memberService;

    //todo 모든 메서드 sms 결제시 구현
    public void sendVerificationCode(String phoneNumber) {

    }

    public boolean isValidCode(String code) {
        if (code.equals("111"))
            return false;

        return true;
    }

    public AuthSmsDto.VerifyCodeResponse verifyCode(AuthSmsDto.VerifyCodeRequest verifyCodeRequest) {
        AuthSmsDto.VerifyCodeResponse.VerifyCodeResponseBuilder verifyBuilder = AuthSmsDto.VerifyCodeResponse.builder();

        if (isValidCode(verifyCodeRequest.getCode())) {
            verifyBuilder.isValidCode(true);
        }

        Optional<Member> requester = memberService.findMemberByPhoneNum(verifyCodeRequest.getPhoneNumber());
        requester.ifPresent(member -> verifyBuilder.alreadySignUpPlatform(member.getSignUpPlatform().getName()));

        return verifyBuilder.build();
    }
}
