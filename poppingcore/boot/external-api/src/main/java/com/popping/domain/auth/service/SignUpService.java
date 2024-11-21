package com.popping.domain.auth.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.service.FriendGroupService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.data.member.service.MemberService;
import com.popping.data.member.service.PolicyTermService;
import com.popping.domain.auth.dto.AuthMemberDto;
import com.popping.domain.auth.dto.TokenDto;
import com.popping.domain.img.service.ImgSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final MemberService memberService;
    private final ImgSaveService imgSaveService;
    private final PolicyTermService policyTermService;
    private final TokenService tokenService;
    private final FriendGroupService friendGroupService;

    @Transactional
    public AuthMemberDto.SignUpResponse signUp(AuthMemberDto.SignUpRequest signUpRequestDto, MultipartFile file) {
        Member requester = signUpRequestDto.of(file);
        memberService.saveMember(requester);
        TokenDto.Tokens tokens = tokenService.createTokens(requester.getPk(), requester.getRole());
        requester.updateRefreshToken(tokens.getRefreshToken().getToken());
        policyTermService.savePolicyTerm(signUpRequestDto.of(requester));
        friendGroupService.saveFriendGroup(FriendGroup.builder().groupOwner(requester).build());

        if (isKakaoProfileImgSaveCond(signUpRequestDto.getSignUpPlatform(), file)) {
            imgSaveService.saveProfileImg(requester.getProfileImgName(), signUpRequestDto.getExtension(), file);
        }

        return new AuthMemberDto.SignUpResponse(tokens);
    }

    protected boolean isKakaoProfileImgSaveCond(SignUpPlatform signUpPlatform, MultipartFile file) {
        return signUpPlatform.equals(SignUpPlatform.KAKAO) && file != null && !file.isEmpty();
    }
}
