package com.popping.domain.member.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.friendgroup.service.FriendContactService;
import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.img.service.ImgService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.pop.service.*;
import com.popping.data.report.service.PopReportService;
import com.popping.data.report.service.RePopReportService;
import com.popping.data.share.service.SharedService;
import com.popping.domain.friend.service.FriendRequestService;
import com.popping.domain.img.service.FindImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawMemberService {
    private final MemberService memberService;
    private final BlockMemberService blockMemberService;
    private final FriendContactService friendContactService;
    private final PopReportService popReportService;
    private final RePopReportService rePopReportService;
    private final PopReadService popReadService;
    private final FriendRequestService friendRequestService;
    private final SharedService sharedService;
    private final RePopActionStateService rePopActionStateService;
    private final RePopService rePopService;
    private final FriendGroupMemberService friendGroupMemberService;
    private final SharedGroupMemberService sharedGroupMemberService;
    private final PopService popService;
    private final PopActionStateService popActionStateService;
    private final ImgService imgService;

    @Transactional
    public void withdrawMember(Long memberPk) {
        Member member = memberService.findMember(memberPk);
        friendContactService.deleteAllAssociatedMember(memberPk);
        friendRequestService.deleteAllAssociatedMember(memberPk);

        sharedService.deleteAllShared(memberPk);

        popReportService.deleteAllAssociatedMember(memberPk);
        rePopReportService.deleteAllAssociatedMember(memberPk);

        imgService.deleteAllAssociatedMember(memberPk);

        rePopActionStateService.deleteAllAssociatedMember(memberPk);
        rePopService.deleteAllAssociatedMember(memberPk);

        popActionStateService.deleteAllAssociatedMember(memberPk);
        popReadService.deleteAllAssociatedMember(memberPk);
        friendGroupMemberService.deleteAllAssociatedMember(memberPk, member.getAllFriendGroup().getPk());
        List<Long> popSharedGroupPks = popService.findAllSharedGroups(memberPk);
        sharedGroupMemberService.deleteAllAssociatedMember(memberPk, popSharedGroupPks);
        popService.deletePops(memberPk);

        blockMemberService.deleteAllAssociatedMember(memberPk);
        memberService.deleteMember(memberPk);
    }
}
