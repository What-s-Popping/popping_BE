package com.popping.domain.friend.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.friendgroup.service.FriendGroupService;
import com.popping.data.member.entity.Member;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.service.PopReadService;
import com.popping.data.pop.service.PopService;
import com.popping.data.report.service.PopReportService;
import com.popping.domain.friend.dto.FindFriendDto;
import com.popping.domain.friend.dto.FindFriendExistenceDto;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.pop.dto.FriendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindFriendService {
    private final FriendGroupService friendGroupService;
    private final FriendGroupMemberService friendGroupMemberService;
    private final PopService popService;
    private final PopReportService popReportService;
    private final BlockMemberService blockMemberService;
    private final FindImgService findImgService;
    private final PopReadService popReadService;

    public FindFriendDto.Response getFriendsForSharing(Long memberPk) {
        FriendGroup friendGroup = friendGroupService.findFriendGroup(memberPk);
        List<FriendGroupMember> friendGroupMembers = friendGroupMemberService.findFriendGroupMembersFetchMember(friendGroup);
        return FindFriendDto.Response.fromFriendGroupMember(friendGroupMembers);
    }

    public FindFriendExistenceDto.Response getFriendExistence(Long requesterPk) {
        return FindFriendExistenceDto.Response.builder()
                .isExist(friendGroupMemberService.findFriendExistence(requesterPk))
                .build();
    }

    public Map<Long, FriendDto.Response> findNotExpiredPopFriends(Long requesterPk) {
        List<Long> blockedMemberPks = blockMemberService.findBlockMemberPks(requesterPk);
        List<Member> friends = popService.findNotExpiredFriends(requesterPk, blockedMemberPks);

        List<Long> reportPopPks = popReportService.findNotExpiredReportPopPks(requesterPk, friends);
        List<Pop> pops = popService.findNotExpiredPops(reportPopPks, friends);
        List<Long> popReadPks = popReadService.findReadPopPks(pops, requesterPk);

        Map<Long, FriendDto.Response> responseDto = new TreeMap<>();

        for (Pop pop : pops) {
            responseDto.computeIfAbsent(pop.getWriterPk(), k -> FriendDto.Response.builder()
                            .nickname(pop.getWriterName())
                            .profileImgUrl(findImgService.generateProfileImgDownloadUrl(pop.getWriter().getProfileImgFileName(), pop.isPrivateProfile()))
                            .build())
                    .getFriendPops().add(FriendDto.FriendPop.builder()
                            .popId(pop.getPk())
                            .isRead(isRead(pop.getPk(), popReadPks))
                            .build());
        }

        return responseDto;
    }

    private static boolean isRead(Long popPk, List<Long> readPops) {
        return readPops.contains(popPk);
    }
}
