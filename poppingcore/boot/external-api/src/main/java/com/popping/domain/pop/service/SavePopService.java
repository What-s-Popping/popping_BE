package com.popping.domain.pop.service;

import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.SharedGroup;
import com.popping.data.pop.entity.SharedGroupMember;
import com.popping.data.pop.service.PopService;
import com.popping.data.pop.service.SharedGroupMemberService;
import com.popping.data.pop.service.SharedGroupService;
import com.popping.domain.notification.dto.FCMDto;
import com.popping.domain.notification.fcmtype.NotificationType;
import com.popping.domain.pop.dto.PopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SavePopService {
    private final PopService popService;
    private final MemberService memberService;
    private final SharedGroupService sharedGroupService;
    private final SharedGroupMemberService sharedGroupMemberService;
    private final FriendGroupMemberService friendGroupMemberService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void savePop(PopDto.Request request, Long memberPk) {
        Member member = memberService.findMember(memberPk);
        if (request.isGlobalShare()) {
            popService.save(Pop.builder()
                    .isPrivateProfile(request.isPrivateProfile())
                    .isGlobalShared(request.isGlobalShare())
                    .chip(request.getChip())
                    .colorChip(request.getColorChip())
                    .contents(request.getContents())
                    .imgName(request.getImgName())
                    .writer(member)
                    .sharedGroup(member.getAllFriendGroup())
                    .build());

            eventPublisher.publishEvent(FCMDto.MulticastFCMEvent.builder()
                    .requesterNickname(member.getName())
                    .notificationType(NotificationType.POP)
                    .targetFcmTokens(friendGroupMemberService.findFriendGroupMemberFCMTokens(member.getAllFriendGroup()))
                    .build()
            );
        }

        if (!request.isGlobalShare()) {
            SharedGroup sharedGroup = new SharedGroup();
            sharedGroupService.saveSharedGroup(sharedGroup);
            List<Long> targetMemberList = request.getTargetMemberList();
            List<Member> members = memberService.findMembers(targetMemberList);
            ArrayList<SharedGroupMember> sharedGroupMembers = new ArrayList<>();
            for (Member member1 : members) {
                SharedGroupMember sharedGroupMember = SharedGroupMember.builder().sharedGroup(sharedGroup).member(member1).build();
                sharedGroupMembers.add(sharedGroupMember);
            }
            sharedGroupMemberService.saveSharedGroupMembers(sharedGroupMembers);

            popService.save(Pop.builder()
                    .isPrivateProfile(request.isPrivateProfile())
                    .isGlobalShared(request.isGlobalShare())
                    .chip(request.getChip())
                    .colorChip(request.getColorChip())
                    .contents(request.getContents())
                    .imgName(request.getImgName())
                    .writer(member)
                    .sharedGroup(sharedGroup)
                    .build());

            eventPublisher.publishEvent(FCMDto.MulticastFCMEvent.builder()
                    .requesterNickname(member.getName())
                    .notificationType(NotificationType.POP)
                    .targetFcmTokens(members.stream().map(Member::getFirebaseToken).toList())
                    .build()
            );
        }
    }
}
