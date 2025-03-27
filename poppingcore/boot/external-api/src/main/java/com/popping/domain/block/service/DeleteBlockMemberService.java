package com.popping.domain.block.service;

import com.popping.data.block.service.BlockMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeleteBlockMemberService {
    private final BlockMemberService blockMemberService;

    @Transactional
    public void deleteBlockMember(Long fromMemberPk, Long toMemberPk) {
        blockMemberService.deleteBlockMember(fromMemberPk, toMemberPk);
    }
}
