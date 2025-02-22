package com.popping.domain.friend.service;

import com.popping.data.friendgroup.service.FriendContactService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.domain.friend.dto.SaveFriendContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveFriendContactService {
    private final FriendContactService friendContactService;
    private final MemberService memberService;
    private static final int CONTACT_BATCH_SIZE = 100;

    @Transactional
    public void saveFriendContacts(Long ownerPk, SaveFriendContactDto.Request requestDto) {
        Member owner = memberService.findMember(ownerPk);
        List<String> filteredPhoneNumbers = requestDto.filter(owner.getPhoneNumber());

        for (int i = 0; i < filteredPhoneNumbers.size(); i += CONTACT_BATCH_SIZE) {
            List<String> subPhoneNumbers = filteredPhoneNumbers.subList(i, Math.min(i + CONTACT_BATCH_SIZE, filteredPhoneNumbers.size()));
            List<Member> friends = memberService.findMembersByPhoneNumber(subPhoneNumbers);

            friendContactService.saveFriendContacts(owner, friends);
        }
    }
}
