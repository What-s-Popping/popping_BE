package com.popping.data.block.service;

import com.popping.data.block.repository.BlockMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockMemberService {
    private final BlockMemberRepository blockMemberRepository;

    public List<Long> findBlockMembers(Long requesterPk) {
        return blockMemberRepository.findBlockMemberPks(requesterPk);
    }

    public void deleteBlockMember(Long fromMemberPk, Long toMemberPk) {
        blockMemberRepository.deleteBlockMember(toMemberPk, fromMemberPk);
    }

    public boolean isExistBlockedHistory(Long fromMemberPk, Long toMemberPk) {
        return blockMemberRepository.existsByFromMember_PkAndToMember_Pk(fromMemberPk, toMemberPk);
    }
}
