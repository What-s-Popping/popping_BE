package com.popping.domain.friend.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import com.popping.data.block.service.BlockMemberService;
import com.popping.data.friendgroup.service.FriendGroupMemberService;
import com.popping.data.friendreqeust.entity.FriendRequest;
import com.popping.data.friendreqeust.repository.FriendRequestRepository;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.share.entity.Shared;
import com.popping.data.share.entity.SharedPlatform;
import com.popping.data.share.entity.SharedType;
import com.popping.data.share.service.SharedService;
import com.popping.domain.friend.dto.FindFriendDto;
import com.popping.domain.friend.dto.FriendRequestDto;
import com.popping.domain.notification.dto.FCMDto;
import com.popping.domain.notification.fcmtype.NotificationType;
import com.popping.domain.notification.service.FCMEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void saveFriendsRequest(Long toMemberPk, Long fromMemberPk) {
        if (friendRequestRepository.findFriendsRequest(toMemberPk, fromMemberPk).isPresent()) {
            return;
        }

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
    public void friendsRequestAccept(Long toMemberPk, Long fromMemberPk) {
        Optional<FriendRequest> friendsRequest = friendRequestRepository.findFriendsRequest(toMemberPk, fromMemberPk);
        if (friendsRequest.isPresent()) {
            friendGroupMemberService.saveFriendGroupMember(toMemberPk, fromMemberPk);
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

    public FindFriendDto.Response findFriendsRequests(Long toMemberPk, Optional<Long> lastId) {
        List<FriendRequest> friendsRequests = friendRequestRepository.findFriendsRequests(toMemberPk,
                lastId.orElse(null),
                PageRequest.ofSize(MAX_PAGE_SIZE)
        );
        return FindFriendDto.Response.fromFriendRequest(friendsRequests);
    }

    public Long findInvitationCode(SharedPlatform platform, Long requesterPk) {
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

    public FriendRequestDto findInvitationInfo(Long key) {
        Shared shared = sharedService.findShared(key);
        String profileImgUrl = s3Service.generateGetPresignedUrl(S3ImgPathPrefix.PROFILE, shared.getSharedMember().getProfileImgFileName());
        return FriendRequestDto.of(shared, profileImgUrl);
    }
}
