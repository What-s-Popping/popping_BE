package com.popping.domain.pop.service;

import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.service.RePopService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.pop.dto.RePopDto;
import com.popping.domain.pop.dto.poptype.PopType;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SaveRePopService {
    private final RePopService rePopService;
    private final MemberService memberService;
    private final FindImgService findImgService;
    private final SavePopActionStateService savePopActionStateService;
    private final SaveRePopActionStateService saveRePopActionStateService;

    @Transactional
    public void save(Long writerPk, RePopDto.Request requestDto) {
        if (requestDto.isExistImg() && findImgService.isNotRePopImgSaved(requestDto.getImgName())) {
            throw new NoSuchElementException(ExceptionMessage.IMG_NOT_SAVED_IN_S3.getMessage());
        }

        Member writer = memberService.findMember(writerPk);
        Member targetMember = memberService.findMember(requestDto.getTargetMemberId());
        RePop rePop = requestDto.of(writer, targetMember);

        rePopService.save(rePop);
        saveActionState(writerPk, requestDto.getTargetPopType(), requestDto.getTargetPopId());
    }

    private void saveActionState(Long writerPk, PopType targetPopType, Long targetPopId) {
        switch (targetPopType) {
            case POP -> savePopActionStateService.saveRePopAction(writerPk, targetPopId);
            case RE_POP -> saveRePopActionStateService.saveRePopAction(writerPk, targetPopId);
        }
    }
}
