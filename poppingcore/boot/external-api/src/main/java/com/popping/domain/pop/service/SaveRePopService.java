package com.popping.domain.pop.service;

import com.popping.data.member.service.MemberService;
import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.service.RePopService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.pop.dto.RePopDto;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SaveRePopService {
    private final RePopService rePopService;
    private final MemberService memberService;
    private final FindImgService findImgService;

    public void save(Long writerPk, RePopDto.Request requestDto) {
        if (requestDto.isExistImg() && findImgService.isNotRePopImgSaved(requestDto.getImgName())) {
            throw new NoSuchElementException(ExceptionMessage.IMG_NOT_SAVED_IN_S3.getMessage());
        }

        rePopService.save(
                RePop.builder()
                        .isPrivateProfile(requestDto.isPrivateProfile())
                        .chip(requestDto.getChip())
                        .colorChip(requestDto.getColorChip())
                        .writer(memberService.findMember(writerPk))
                        .targetMember(memberService.findMember(requestDto.getTargetMember()))
                        .imgName(requestDto.getImgName())
                        .contents(requestDto.getContents())
                        .build()
        );
    }
}
