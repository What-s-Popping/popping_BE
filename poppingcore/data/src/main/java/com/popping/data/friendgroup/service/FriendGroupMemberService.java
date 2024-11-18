package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.friendgroup.repository.FriendGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendGroupMemberService {
    private final FriendGroupMemberRepository friendGroupMemberRepository;

    public List<FriendGroupMember> findFriendGroupMembersFetchMember(FriendGroup friendGroup) {
        return friendGroupMemberRepository.findFriendGroupMembersFetchMember(friendGroup);
    }
}
