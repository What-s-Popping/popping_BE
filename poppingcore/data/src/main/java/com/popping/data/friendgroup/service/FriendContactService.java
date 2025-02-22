package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.FriendContact;
import com.popping.data.friendgroup.repository.FriendContactRepository;
import com.popping.data.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendContactService {
    private final FriendContactRepository friendContactRepository;

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
}
