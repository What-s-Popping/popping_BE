package com.popping.domain.block;

import com.popping.data.block.service.BlockMemberService;
import com.popping.domain.block.dto.BlockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindBlockMemberService {
    private final BlockMemberService blockMemberService;

    public BlockDto.Response isExistBlockedHistory(Long toMemberPk, Long fromMemberPk) {
        return BlockDto.Response.builder()
                .isBlocked(blockMemberService.isExistBlockedHistory(fromMemberPk, toMemberPk))
                .build();
    }
}
