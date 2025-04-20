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
                    .isWidgetAllow(request.isWidgetAllow())
                    .chip(request.getChip())
                    .colorChip(request.getColorChip())
                    .contents(request.getContents())
                    .imgName(request.getImgName())
                    .writer(member)
                    .sharedGroup(member.getAllFriendGroup())
                    .build());

            // todo firebase Project 생성 시 주석 풀기
//            List<Member> friendGroupMembers = friendGroupMemberService.findFriendGroupMembers(member.getAllFriendGroup());
//            eventPublisher.publishEvent(FCMDto.MulticastFCMEvent.builder()
//                    .requesterNickname(member.getName())
//                    .notificationType(NotificationType.POP)
//                    .targetFcmTokens(filteredAllowNotifyFCMTokens(friendGroupMembers))
//            );
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
                    .isWidgetAllow(request.isWidgetAllow())
                    .chip(request.getChip())
                    .colorChip(request.getColorChip())
                    .contents(request.getContents())
                    .imgName(request.getImgName())
                    .writer(member)
                    .sharedGroup(sharedGroup)
                    .build());

            // todo firebase Project 생성 시 주석 풀기
//            eventPublisher.publishEvent(FCMDto.MulticastFCMEvent.builder()
//                    .requesterNickname(member.getName())
//                    .notificationType(NotificationType.POP)
//                    .targetFcmTokens(filteredAllowNotifyFCMTokens(members))
//                    .build()
//            );
        }
    }

    private static List<String> filteredAllowNotifyFCMTokens(List<Member> members) {
        return members.stream()
                .filter(Member::isAllowPopNotify)
                .map(Member::getFirebaseToken)
                .toList();
    }
}
