package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.FriendContact;
import com.popping.data.friendgroup.repository.FriendContactRepository;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendContactService {
    private final FriendContactRepository friendContactRepository;
    private final MemberService memberService;

    public void saveFriendContacts(Member owner, List<Member> friends) {
        List<FriendContact> friendContacts = friends.stream().map(
                friend -> FriendContact.builder()
                        .friend(friend)
                        .owner(owner)
                        .build()
                )
                .toList();

        friendContactRepository.saveAll(friendContacts);
    }

    public List<Member> findFriends(Long requesterPk) {
        List<Long> friendsPk = friendContactRepository.findFriends(requesterPk);
        return memberService.findMembers(friendsPk);
    }

    @Transactional
    public void deleteAllAssociatedMember(Long memberPk) {
        friendContactRepository.deleteAllAssociatedMember(memberPk);
    }
}
