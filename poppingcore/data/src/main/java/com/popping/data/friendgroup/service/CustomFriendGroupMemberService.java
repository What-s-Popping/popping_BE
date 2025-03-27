package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.CustomFriendGroup;
import com.popping.data.friendgroup.entity.CustomFriendGroupMember;
import com.popping.data.friendgroup.repository.CustomFriendGroupMemberRepository;
import com.popping.data.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomFriendGroupMemberService {
    private final CustomFriendGroupMemberRepository customFriendGroupMemberRepository;

    public void saveCustomFriendGroupMembers(CustomFriendGroup customFriendGroup, List<Member> members) {
        List<CustomFriendGroupMember> customFriendGroupMembers = members.stream().map(member -> CustomFriendGroupMember.builder()
                .customFriendGroup(customFriendGroup)
                .member(member)
                .build()
                ).toList();
        customFriendGroupMemberRepository.saveAll(customFriendGroupMembers);
    }

    public List<CustomFriendGroupMember> findCustomFriendGroupMembers(CustomFriendGroup customFriendGroup) {
        return customFriendGroupMemberRepository.findCustomFriendGroupMembers(customFriendGroup);
    }

    @Transactional
    public void deleteAll(List<CustomFriendGroupMember> customFriendGroupMembers) {
        customFriendGroupMemberRepository.deleteAllInBatch(customFriendGroupMembers);
    }

    public CustomFriendGroupMember findCustomFriendGroupMember(CustomFriendGroup customFriendGroup, long memberId) {
        return customFriendGroupMemberRepository.findCustomFriendGroupMember(customFriendGroup, memberId)
                .orElseThrow(() -> new IllegalArgumentException("CustomFriendGroupMember not found"));
    }

    public void delete(CustomFriendGroupMember customFriendGroupMember) {
        customFriendGroupMemberRepository.delete(customFriendGroupMember);
    }

    public List<CustomFriendGroupMember> findCustomFriendGroupMembers(List<CustomFriendGroup> customFriendGroups) {
        return customFriendGroupMemberRepository.findCustomFriendGroupMembers(customFriendGroups);
    }

    public CustomFriendGroupMember saveCustomFriendGroupMember(CustomFriendGroupMember customFriendGroupMember) {
        return customFriendGroupMemberRepository.save(customFriendGroupMember);
    }

    public void deleteCustomFriendGroupMember(List<CustomFriendGroup> memberCustomFriendGroup, long requesterPk) {
        customFriendGroupMemberRepository.deleteCustomFriendGroupMember(memberCustomFriendGroup, requesterPk);
    }
}
