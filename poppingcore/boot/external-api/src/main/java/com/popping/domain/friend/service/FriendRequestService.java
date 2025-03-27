package com.popping.domain.friend.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import com.popping.data.block.service.BlockMemberService;
import com.popping.data.friendgroup.entity.CustomFriendGroup;
import com.popping.data.friendgroup.entity.CustomFriendGroupMember;
import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.service.CustomFriendGroupMemberService;
import com.popping.data.friendgroup.service.CustomFriendGroupService;
import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.friendgroup.service.FriendGroupService;
import com.popping.data.friendreqeust.entity.FriendRequest;
import com.popping.data.friendreqeust.repository.FriendRequestRepository;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.service.*;
import com.popping.data.share.entity.Shared;
import com.popping.data.share.entity.SharedPlatform;
import com.popping.data.share.entity.SharedType;
import com.popping.data.share.service.SharedService;
import com.popping.domain.friend.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private static final int MAX_PAGE_SIZE = 50;

    private final MemberService memberService;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendGroupMemberService friendGroupMemberService;
    private final SharedService sharedService;
    private final BlockMemberService blockMemberService;
    private final S3Service s3Service;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomFriendGroupService customFriendGroupService;
    private final CustomFriendGroupMemberService customFriendGroupMemberService;
    private final RePopService rePopService;
    private final PopService popService;
    private final PopReadService popReadService;
    private final PopActionStateService popActionStateService;
    private final FriendGroupService friendGroupService;
    private final SharedGroupMemberService sharedGroupMemberService;

    @Transactional
    public void saveFriendsRequest(Long toMemberPk, Long fromMemberPk) {
        if (friendRequestRepository.findFriendsRequest(toMemberPk, fromMemberPk).isPresent()) {
            return;
        }
        blockMemberService.deleteBlockMember(fromMemberPk, toMemberPk);

        Member fromMember = memberService.findMember(fromMemberPk);
        Member toMember = memberService.findMember(toMemberPk);
        FriendRequest friendRequest = FriendRequest.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
        friendRequestRepository.save(friendRequest);

        // todo firebase Project 생성 시 주석 풀기
//        eventPublisher.publishEvent(
//                FCMDto.FCMEvent.builder()
//                        .requesterNickname(fromMember.getName())
//                        .targetFcmToken(toMember.getFirebaseToken())
//                        .notificationType(NotificationType.FRIEND_REQUEST)
//                        .build()
//        );
    }

    @Transactional
    public void friendsRequestAccept(long toMemberPk, long fromMemberPk) {
        Optional<FriendRequest> friendsRequest = friendRequestRepository.findFriendsRequest(toMemberPk, fromMemberPk);
        if (friendsRequest.isPresent()) {
            friendGroupMemberService.saveFriendGroupMember(toMemberPk, fromMemberPk);
            friendGroupMemberService.saveFriendGroupMember(fromMemberPk, toMemberPk);
            friendRequestRepository.delete(friendsRequest.get());
        }

        // todo firebase Project 생성 시 주석 풀기
//        eventPublisher.publishEvent(
//                FCMDto.FCMEvent.builder()
//                        .requesterNickname(memberService.findMember(fromMemberPk).getName())
//                        .targetFcmToken(memberService.findMember(toMemberPk).getFirebaseToken())
//                        .notificationType(NotificationType.FRIEND_REQUEST_APPROVE)
//                        .build()
//        );
    }

    public FindFriendDto.Response findFriendsRequests(long toMemberPk, Optional<Long> lastId) {
        List<FriendRequest> friendsRequests = friendRequestRepository.findFriendsRequests(toMemberPk,
                lastId.orElse(null),
                PageRequest.ofSize(MAX_PAGE_SIZE)
        );
        return FindFriendDto.Response.fromFriendRequest(friendsRequests);
    }

    public Long findInvitationCode(SharedPlatform platform, long requesterPk) {
        Optional<Shared> sharedOpt = sharedService.findShared(platform, SharedType.PROFILE, requesterPk);
        if (sharedOpt.isPresent()) {
            return sharedOpt.get().getPk();
        }else {
            Member member = memberService.findMember(requesterPk);
            Shared shared = Shared.builder().sharedMember(member)
                    .sharedPlatform(platform)
                    .sharedType(SharedType.PROFILE)
                    .build();
            sharedService.saveShared(shared);
            return shared.getPk();
        }
    }

    @Transactional
    public FriendRequestDto findInvitationInfo(long key) {
        Shared shared = sharedService.findShared(key);
        String profileImgUrl = s3Service.generateGetPresignedUrl(S3ImgPathPrefix.PROFILE, shared.getSharedMember().getProfileImgFileName());
        return FriendRequestDto.of(shared, profileImgUrl);
    }

    public void saveCustomFriendsGroup(SaveCustomFriendGroupDto request, long requesterPk) {
        Member requester = memberService.findMember(requesterPk);
        List<Member> members = memberService.findMembers(request.getFriendPks());
        CustomFriendGroup customFriendGroup = customFriendGroupService.saveCustomFriendGroup(request.getGroupName(), requester);

        customFriendGroupMemberService.saveCustomFriendGroupMembers(customFriendGroup, members);
    }

    public void updateCustomFriendsGroupName(long groupId, UpdateCustomFriendGroupNameDto request, long requesterPk) {
        customFriendGroupService.updateCustomFriendGroupName(groupId, request.getCustomGroupName(), requesterPk);
    }

    @Transactional
    public void deleteCustomFriendsGroup(long groupId, long requesterPk) {
        CustomFriendGroup customFriendGroup = customFriendGroupService.findCustomFriendGroup(groupId, requesterPk);
        List<CustomFriendGroupMember> customFriendGroupMembers = customFriendGroupMemberService.findCustomFriendGroupMembers(customFriendGroup);
        customFriendGroupMemberService.deleteAll(customFriendGroupMembers);
        customFriendGroupService.delete(customFriendGroup);
        s3Service.deleteCustomFriendGroupImg(customFriendGroup.getGroupImgName());
    }

    @Transactional
    public void deleteCustomFriendsGroupMember(long groupId, long memberId, long requesterPk) {
        CustomFriendGroup customFriendGroup = customFriendGroupService.findCustomFriendGroup(groupId, requesterPk);
        CustomFriendGroupMember customFriendGroupMember = customFriendGroupMemberService.findCustomFriendGroupMember(customFriendGroup, memberId);
        customFriendGroupMemberService.delete(customFriendGroupMember);
    }

    public List<FindCustomFriendGroupDto> findCustomFriendsGroups(long requesterPk) {
        List<CustomFriendGroup> customFriendGroups = customFriendGroupService.findCustomFriendGroups(requesterPk);
        List<CustomFriendGroupMember> customFriendGroupMembers = customFriendGroupMemberService.findCustomFriendGroupMembers(customFriendGroups);

        return customFriendGroups.stream().collect(Collectors.toMap(
                                customFriendGroup -> customFriendGroup,
                                customFriendGroup -> customFriendGroupMembers.stream()
                                        .filter(customFriendGroupMember -> customFriendGroupMember.getCustomFriendGroup().equals(customFriendGroup))
                                        .map(groupMemberInCustomGroup -> FindCustomFriendGroupDto.FriendGroupMember.builder()
                                                        .userId(groupMemberInCustomGroup.getMember().getPk())
                                                        .userName(groupMemberInCustomGroup.getMember().getName())
                                                        .profileImgUrl(s3Service.generateGetPresignedUrl(S3ImgPathPrefix.PROFILE, groupMemberInCustomGroup.getMember().getProfileImgFileName()))
                                                        .build()
                                        ).toList()
                        )
                ).entrySet().stream()
                .map(entry -> FindCustomFriendGroupDto.builder()
                        .groupId(entry.getKey().getPk())
                        .groupName(entry.getKey().getGroupName())
                        .groupMembers(entry.getValue())
                        .build()
                ).toList();
    }

    public void saveNewCustomFriendsGroupMember(long groupId, long memberId, long requesterPk) {
        Member newMember = memberService.findMember(memberId);
        CustomFriendGroup customFriendGroup = customFriendGroupService.findCustomFriendGroup(groupId, requesterPk);
        customFriendGroupMemberService.saveCustomFriendGroupMember(CustomFriendGroupMember.builder()
                .customFriendGroup(customFriendGroup)
                .member(newMember)
                .build());
    }

    @Transactional
    public void deleteFriend(long memberId, long requesterPk) {
        List<RePop> rePops = rePopService.findRePops(memberId, requesterPk);
        rePops.addAll(rePopService.findRePops(requesterPk, memberId));
        rePopService.deleteRePops(rePops);

        popReadService.deleteReadPopsHistory(memberId, requesterPk);
        popReadService.deleteReadPopsHistory(requesterPk, memberId);

        popActionStateService.deletePopActionState(memberId, requesterPk);
        popActionStateService.deletePopActionState(requesterPk, memberId);

        FriendGroup memberGroup = friendGroupService.findFriendGroup(memberId);
        FriendGroup requesterGroup = friendGroupService.findFriendGroup(requesterPk);

        friendGroupMemberService.deleteFriendGroupMember(memberGroup, requesterPk);
        friendGroupMemberService.deleteFriendGroupMember(requesterGroup, memberId);

        List<Long> memberAllSharedGroupPk = popService.findAllMyPop(memberId).stream().map(pop -> pop.getSharedGroup().getPk()).toList();
        List<Long> requesterAllSharedGroupPk = popService.findAllMyPop(requesterPk).stream().map(pop -> pop.getSharedGroup().getPk()).toList();

        sharedGroupMemberService.deleteSharedGroupMember(memberAllSharedGroupPk, requesterPk);
        sharedGroupMemberService.deleteSharedGroupMember(requesterAllSharedGroupPk, memberId);

        List<CustomFriendGroup> memberCustomFriendGroup = customFriendGroupService.findCustomFriendGroups(memberId);
        List<CustomFriendGroup> requesterCustomFriendGroup = customFriendGroupService.findCustomFriendGroups(requesterPk);

        customFriendGroupMemberService.deleteCustomFriendGroupMember(memberCustomFriendGroup, requesterPk);
        customFriendGroupMemberService.deleteCustomFriendGroupMember(requesterCustomFriendGroup, memberId);
    }

    @Transactional
    public void deleteAllAssociatedMember(Long memberPk) {
        friendRequestRepository.deleteAllAssociatedMember(memberPk);
    }
}
