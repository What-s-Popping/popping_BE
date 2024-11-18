package com.popping.domain.member.service;

import com.popping.data.member.data.member.service.MemberService;
import com.popping.domain.member.dto.PopcornDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopcornService {
    private final MemberService memberService;

    public PopcornDto.Response getPopcornBalance(Long memberPk) {
        return PopcornDto.Response.builder()
                .popcornBalance(
                        memberService.findPopcornBalance(memberPk)
                ).build();
    }
}
