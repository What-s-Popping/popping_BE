package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.CustomFriendGroup;
import com.popping.data.friendgroup.repository.CustomFriendGroupRepository;
import com.popping.data.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomFriendGroupService {

    private final CustomFriendGroupRepository customFriendGroupRepository;

    public CustomFriendGroup saveCustomFriendGroup(String groupName, Member groupOwner){
        return customFriendGroupRepository.save(
                CustomFriendGroup.builder()
                        .groupOwner(groupOwner)
                        .groupName(groupName)
                        .build()
        );
    }

    public void updateCustomFriendGroupName(long groupId, String customGroupName, Long requesterPk) {
        customFriendGroupRepository.updateCustomFriendGroupName(groupId, customGroupName, requesterPk);
    }

    public CustomFriendGroup findCustomFriendGroup(long groupId, long requesterPk) {
        return customFriendGroupRepository.findCustomFriendGroup(groupId, requesterPk)
                .orElseThrow(() -> new IllegalArgumentException("CustomFriendGroupMember not found"));
    }

    @Transactional
    public void delete(CustomFriendGroup customFriendGroup) {
        customFriendGroupRepository.delete(customFriendGroup);
    }

    public List<CustomFriendGroup> findCustomFriendGroups(long groupOwnerPk) {
        return customFriendGroupRepository.findCustomFriendGroups(groupOwnerPk);
    }
}
