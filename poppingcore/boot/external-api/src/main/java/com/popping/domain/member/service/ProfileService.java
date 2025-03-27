package com.popping.domain.member.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.domain.member.dto.MemberInfoDto;
import com.popping.domain.member.dto.MemberNameDto;
import com.popping.domain.member.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberService memberService;
    private final S3Service s3Service;

    public MemberInfoDto.Response findNameAndProfileImg(Long memberPk){
        Member member = memberService.findMemberOp(memberPk).orElseThrow(NoSuchElementException::new);
        String name = member.getName();
        String profileImgUrl = null;
        if (s3Service.isImgSaved(S3ImgPathPrefix.PROFILE, member.getProfileImgFileName())) {
            profileImgUrl = s3Service.generateGetPresignedUrl(S3ImgPathPrefix.PROFILE, member.getProfileImgFileName());
        }

        return MemberInfoDto.Response.builder().name(name).profileImgUrl(profileImgUrl).build();
    }

    public ProfileDto.Response generateProfileImgUploadUrl(Long memberPk) {
        Member member = memberService.findMemberOp(memberPk).orElseThrow(NoSuchElementException::new);
        String imgName = member.getProfileImgFileName();
        String putPresignedUrl = s3Service.generatePutPresignedUrl(S3ImgPathPrefix.PROFILE, imgName);
        return ProfileDto.Response.builder()
                .profileImgUploadUrl(putPresignedUrl)
                .imgName(imgName)
                .build();
    }

    @Transactional
    public void updateProfileName(Long memberPk, ProfileDto.Request request) {
        memberService.findMemberOp(memberPk).orElseThrow(NoSuchElementException::new)
                .updateName(request.getUpdateName());
    }

    public MemberNameDto.Response findName(Long memberPk) {
        return MemberNameDto.Response.builder()
                .name(memberService.findMemberName(memberPk))
                .build();
    }
}
