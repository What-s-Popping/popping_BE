package com.popping.domain.auth.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.service.FriendGroupService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.data.member.service.MemberService;
import com.popping.data.member.service.PolicyTermService;
import com.popping.domain.auth.dto.AuthMemberDto;
import com.popping.domain.auth.dto.TokenDto;
import com.popping.domain.img.service.SaveImgService;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final MemberService memberService;
    private final SaveImgService saveImgService;
    private final PolicyTermService policyTermService;
    private final TokenService tokenService;
    private final FriendGroupService friendGroupService;

    @Transactional
    public AuthMemberDto.SignUpResponse signUp(AuthMemberDto.SignUpRequest requestDto) {
        verifySignUpCond(requestDto);

        Member requester = requestDto.of();
        memberService.saveMember(requester);
        TokenDto.Tokens tokens = tokenService.createTokens(requester.getPk(), requester.getRole());
        requester.updateRefreshToken(tokens.getRefreshToken().getToken());
        policyTermService.savePolicyTerm(requestDto.of(requester));
        friendGroupService.saveFriendGroup(FriendGroup.builder().groupOwner(requester).build());

        if (isKakaoProfileImgSaveCond(requestDto.getSignUpPlatform(), requestDto.getFile())) {
            saveImgService.saveProfileImg(requester.getPk().toString(), requestDto.getExtension(), requestDto.getFile());
        }

        return new AuthMemberDto.SignUpResponse(tokens);
    }

    private void verifySignUpCond(AuthMemberDto.SignUpRequest requestDto) {
        if (requestDto.isExistNotAllowPolicies()) {
            throw new IllegalStateException(ExceptionMessage.ALL_POLICIES_NOT_AGREED.getMessage());
        }

        if (memberService.isSignUpMember(requestDto.getEmail(), requestDto.getSignUpPlatform())) {
            throw new EntityExistsException(ExceptionMessage.ALREADY_SIGN_UP_EMAIL.getMessage());
        }
    }

    protected boolean isKakaoProfileImgSaveCond(SignUpPlatform signUpPlatform, MultipartFile file) {
        return signUpPlatform.equals(SignUpPlatform.KAKAO) && file != null && !file.isEmpty();
    }
}
