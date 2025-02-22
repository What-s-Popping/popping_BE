package com.popping.domain.friend.service;

import com.popping.data.friendgroup.service.FriendContactService;
import com.popping.data.member.entity.Member;
import com.popping.domain.friend.dto.FindFriendContactDto;
import com.popping.domain.img.service.FindImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindFriendContactService {
    private final FriendContactService friendContactService;
    private final FindImgService findImgService;

    public List<FindFriendContactDto.Response> findFriendsMayKnow(Long requester) {
        List<Member> friends = friendContactService.findFriends(requester);

        return friends.stream().map(friend -> FindFriendContactDto.Response.builder()
                .member(friend)
                .profileImgUrl(findImgService.generateProfileImgDownloadUrl(friend.getPk().toString()))
                .build())
                .toList();
    }
}
