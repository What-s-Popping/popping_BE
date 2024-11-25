package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.friendgroup.repository.FriendGroupMemberRepository;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendGroupMemberService {
    private final FriendGroupMemberRepository friendGroupMemberRepository;
    private final MemberService memberService;

    public List<FriendGroupMember> findFriendGroupMembersFetchMember(FriendGroup friendGroup) {
        return friendGroupMemberRepository.findFriendGroupMembersFetchMember(friendGroup);
    }

    public boolean findFriendExistence(Long requesterPk) {
        Member requester = memberService.findMember(requesterPk);
        return friendGroupMemberRepository.existsByFriendGroup(requester.getAllFriendGroup());
    }
}
