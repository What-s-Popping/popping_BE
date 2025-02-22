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

import java.util.List;
import java.util.Optional;

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

    public List<FriendDto.Response> findNotExpiredPopFriends(Optional<Long> lastFriendPk, Long requesterPk) {
        List<Long> blockedMemberPks = blockMemberService.findBlockMembers(requesterPk);
        List<Member> friends = popService.findNotExpiredFriends(lastFriendPk, requesterPk, blockedMemberPks);

        List<Long> reportPopPks = popReportService.findNotExpiredReportPopPks(requesterPk, friends);
        List<Pop> pops = popService.findNotExpiredPops(reportPopPks, friends);
        List<Long> popReads = popReadService.findReadPopPks(pops, requesterPk);

        return friends.stream()
                .map(friend -> FriendDto.Response.builder()
                        .id(friend.getPk())
                        .nickname(friend.getName())
                        .profileImgUrl(findImgService.generateProfileImgDownloadUrl(friend.getProfileImgFileName()))
                        .friendPopIds(extractFriendPopIds(friend.getPk(), pops))
                        .isRead(isRead(friend.getPk(), pops, popReads))
                        .build()
                )
                .toList();
    }

    private static List<Long> extractFriendPopIds(Long writerPk, List<Pop> pops) {
        return pops.stream()
                .filter(pop -> pop.isWriter(writerPk))
                .map(Pop::getPk)
                .toList();
    }

    private boolean isRead(Long writerPk, List<Pop> pops, List<Long> readPops) {
        return pops.stream()
                .filter(pop -> pop.isWriter(writerPk))
                .allMatch(pop -> readPops.contains(pop.getPk()));
    }
}
