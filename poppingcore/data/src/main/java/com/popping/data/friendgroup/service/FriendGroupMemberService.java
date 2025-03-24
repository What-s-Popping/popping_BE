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
    private final FriendGroupService friendGroupService;

    public List<FriendGroupMember> findFriendGroupMembersFetchMember(FriendGroup friendGroup) {
        return friendGroupMemberRepository.findFriendGroupMembersFetchMember(friendGroup);
    }

    public boolean findFriendExistence(Long requesterPk) {
        Member requester = memberService.findMember(requesterPk);
        return friendGroupMemberRepository.existsByFriendGroup(requester.getAllFriendGroup());
    }

    public void saveFriendGroupMember(Long groupOwnerPk, Long groupMemberPk) {

        if (!isAlreadySaved(groupOwnerPk, groupMemberPk)) {
            FriendGroup friendGroup = friendGroupService.findFriendGroup(groupOwnerPk);
            Member groupMember = memberService.findMember(groupMemberPk);

            FriendGroupMember friendGroupMember = FriendGroupMember.builder()
                    .friendGroup(friendGroup)
                    .member(groupMember)
                    .build();

            friendGroupMemberRepository.save(friendGroupMember);
        }
    }

    private boolean isAlreadySaved(Long groupOwnerPk, Long groupMemberPk) {
        return friendGroupMemberRepository.findFriendGroupMember(groupOwnerPk, groupMemberPk).isEmpty();
    }

    public List<Member> findFriendGroupMembers(FriendGroup friendGroup) {
        return friendGroupMemberRepository.findFriendGroupMembers(friendGroup);
    }

    public void deleteFriendGroupMember(FriendGroup friendGroup, long requesterPk) {
        friendGroupMemberRepository.deleteFriendGroupMember(friendGroup, requesterPk);
    }
}
