package com.popping.domain.notification.service;

import com.popping.data.member.service.MemberService;
import com.popping.domain.notification.dto.NotificationStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MemberService memberService;

    public NotificationStatusDto.Response findAllowAllNotify(Long memberPk) {
        return NotificationStatusDto.Response.builder()
                .isAllowNotify(memberService.findMember(memberPk).isAllowNotify())
                .build();
    }

    public NotificationStatusDto.Response findAllowPopNotify(Long memberPk) {
        return NotificationStatusDto.Response.builder()
                .isAllowNotify(memberService.findMember(memberPk).isAllowFriendPopNotify())
                .build();
    }

    @Transactional
    public void updateAllNotify(NotificationStatusDto.Request request, Long memberPk) {
        memberService.findMember(memberPk)
                .updateAllowNotify(request.isAllowNotify());
    }

    @Transactional
    public void updateAllowPopNotify(NotificationStatusDto.Request request, Long memberPk) {
        memberService.findMember(memberPk)
                .updateAllowPopNotify(request.isAllowNotify());
    }
}
