package com.popping.domain.friend.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.friendgroup.service.FriendGroupService;
import com.popping.domain.friend.dto.FindFriendDto;
import com.popping.domain.friend.dto.FindFriendExistenceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindFriendService {
    private final FriendGroupService friendGroupService;
    private final FriendGroupMemberService friendGroupMemberService;

    public FindFriendDto.Response getFriendsForSharing(Long memberPk) {
        FriendGroup friendGroup = friendGroupService.findFriendGroup(memberPk);
        List<FriendGroupMember> friendGroupMembers = friendGroupMemberService.findFriendGroupMembersFetchMember(friendGroup);
        return FindFriendDto.Response.of(friendGroupMembers);
    }

    public FindFriendExistenceDto.Response getFriendExistence(Long requesterPk) {
        return FindFriendExistenceDto.Response.builder()
                .isExist(friendGroupMemberService.findFriendExistence(requesterPk))
                .build();
    }
}
