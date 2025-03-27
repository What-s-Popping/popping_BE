package com.popping.domain.block.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.member.entity.Member;
import com.popping.domain.block.dto.BlockDto;
import com.popping.domain.block.dto.BlockMemberDto;
import com.popping.domain.img.service.FindImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindBlockMemberService {
    private final BlockMemberService blockMemberService;
    private final FindImgService findImgService;

    public BlockDto.Response isExistBlockedHistory(Long toMemberPk, Long fromMemberPk) {
        return BlockDto.Response.builder()
                .isBlocked(blockMemberService.isExistBlockedHistory(fromMemberPk, toMemberPk))
                .build();
    }

    public List<BlockMemberDto.Response> findBlockMembers(Long requesterPk) {
        List<Member> blockMembers = blockMemberService.findBlockMembers(requesterPk);

        return blockMembers.stream().map(member -> BlockMemberDto.Response.builder()
                .member(member)
                .profileImgUrl(findImgService.generateProfileImgDownloadUrl(member.getProfileImgFileName()))
                .build())
                .toList();
    }
}
