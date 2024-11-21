package com.popping.domain.auth.service;

import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.role.Role;
import com.popping.data.member.service.MemberService;
import com.popping.domain.auth.dto.TokenDto;
import com.popping.global.config.jwt.TokenProvider;
import com.popping.global.exception.SignInRequiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    private static final String BEARER = "Bearer ";

    public TokenDto.Tokens createTokens(Long memberPk, Role role) {
        return TokenDto.Tokens.builder()
                .refreshToken(createRefreshToken(memberPk, role))
                .accessToken(createAccessToken(memberPk, role))
                .build();
    }

    public TokenDto.Token createRefreshToken(Long memberPk, Role role) {
        String refreshToken = tokenProvider.createRefreshToken(memberPk, role);
        return TokenDto.Token.builder()
                .token(refreshToken)
                .expiredTime(getExpirationTime(refreshToken))
                .build();
    }

    public TokenDto.Token createAccessToken(Long memberPk, Role role) {
        String accessToken = tokenProvider.createAccessToken(memberPk, role);
        return TokenDto.Token.builder()
                .token(accessToken)
                .expiredTime(getExpirationTime(accessToken))
                .build();
    }

    public boolean isExpired(String token) {
        return tokenProvider.isExpired(token);
    }

    public boolean isNotValidToken(String authorizationHeaderValue) {
        return !tokenProvider.isValidToken(getToken(authorizationHeaderValue));
    }

    public Date getExpirationTime(String token) {
        return tokenProvider.getExpirationTime(token);
    }

    @Transactional
    public TokenDto.TokenResponse reIssueTokens(String authorizationHeaderValue) {
        String refreshToken = getToken(authorizationHeaderValue);

        if (isNotValidRefreshToken(refreshToken)) {
            throw new SignInRequiredException();
        }

        Member requester = memberService.findMemberByToken(refreshToken);
        TokenDto.Tokens tokens = createTokens(requester.getPk(), requester.getRole());
        requester.updateRefreshToken(tokens.getRefreshToken().getToken());

        return TokenDto.TokenResponse.builder().tokens(tokens).build();
    }

    public boolean isNotValidRefreshToken(String refreshToken) {
        return isNotValidToken(refreshToken) || !tokenProvider.isRefreshToken(refreshToken);
    }

    public String getToken(String authorizationHeaderValue) {
        return authorizationHeaderValue == null
                ? null
                : authorizationHeaderValue.replace(BEARER, "");
    }
}
