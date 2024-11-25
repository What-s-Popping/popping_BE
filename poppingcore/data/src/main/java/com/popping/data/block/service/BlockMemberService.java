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
}
