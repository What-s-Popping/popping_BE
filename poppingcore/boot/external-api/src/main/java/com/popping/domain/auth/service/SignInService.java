package com.popping.domain.auth.service;

import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.domain.auth.dto.AuthMemberDto;
import com.popping.domain.auth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final MemberService memberService;
    private final TokenService tokenService;

    @Transactional
    public AuthMemberDto.SignInResponse signIn(AuthMemberDto.SignInRequest signInRequestDto) {
        Optional<Member> requesterOp = memberService.findMember(signInRequestDto.getEmail(), signInRequestDto.getSignUpPlatform());

        if (requesterOp.isEmpty()) {
            return AuthMemberDto.SignInResponse.of(false);
        }

        Member requester = requesterOp.get();
        TokenDto.Tokens tokens = tokenService.createTokens(requester.getPk(), requester.getRole());
        requester.updateRefreshToken(tokens.getRefreshToken().getToken());
        return AuthMemberDto.SignInResponse.of(true, tokens, requester);
    }

    public AuthMemberDto.SignInRequiredResponse verifySignInRequired(String authorizationHeaderValue) {
        String refreshToken = tokenService.getToken(authorizationHeaderValue);

        if (tokenService.isNotValidRefreshToken(refreshToken)) {
            return AuthMemberDto.SignInRequiredResponse.builder().isSignInRequired(true).build();
        }

        Member requester = memberService.findMemberByToken(refreshToken);
        TokenDto.Token accessToken = tokenService.createAccessToken(requester.getPk(), requester.getRole());

        return AuthMemberDto.SignInRequiredResponse.builder()
                .isSignInRequired(false)
                .accessToken(accessToken)
                .build();
    }
}
