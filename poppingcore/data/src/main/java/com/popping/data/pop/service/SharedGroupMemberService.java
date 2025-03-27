package com.popping.data.pop.service;

import com.popping.data.pop.entity.SharedGroupMember;
import com.popping.data.pop.repository.SharedGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedGroupMemberService {
    private final SharedGroupMemberRepository sharedGroupMemberRepository;
    public void saveSharedGroupMember(SharedGroupMember sharedGroupMember) {
        sharedGroupMemberRepository.save(sharedGroupMember);
    }

    public void saveSharedGroupMembers(List<SharedGroupMember> sharedGroupMembers) {
        sharedGroupMemberRepository.saveAll(sharedGroupMembers);
    }

    @Transactional
    public void deleteAllAssociatedMember(Long memberPk, List<Long> sharedGroupPks) {
        sharedGroupMemberRepository.deleteAllAssociatedMember(memberPk, sharedGroupPks);
    }

    public void deleteSharedGroupMember(List<Long> memberAllSharedGroupPk, long requesterPk) {
        sharedGroupMemberRepository.deleteSharedGroupMember(memberAllSharedGroupPk, requesterPk);
    }
}
