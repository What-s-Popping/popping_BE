package com.popping.domain.friend.controller;

import com.popping.data.share.entity.SharedPlatform;
import com.popping.domain.friend.dto.FindFriendDto;
import com.popping.domain.friend.dto.FriendRequestDto;
import com.popping.domain.friend.dto.FriendsRequestToMemberDto;
import com.popping.domain.friend.service.FindFriendService;
import com.popping.domain.friend.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FindFriendService findFriendService;
    private final FriendRequestService friendRequestService;

    @GetMapping("/sharable")
    public ResponseEntity<FindFriendDto.Response> getFriendsList(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendService.getFriendsForSharing(Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/existence")
    public ResponseEntity<?> getFriendExistence(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendService.getFriendExistence(Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/pops")
    public ResponseEntity<?> getNotExpiredPopFriends(@RequestParam Optional<Long> lastFriendId,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendService.findNotExpiredPopFriends(lastFriendId, Long.valueOf(userDetails.getUsername())));
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> saveFriendsRequest(@RequestBody FriendsRequestToMemberDto toMemberPk,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.saveFriendsRequest(toMemberPk.getToMemberPk(), Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/accept")
    public ResponseEntity<Void> saveFriendsRequestAccept(@RequestBody FriendsRequestToMemberDto toMemberPk,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.friendsRequestAccept(toMemberPk.getToMemberPk(), Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/requests","/requests/{lastId}"})
    public ResponseEntity<FindFriendDto.Response> getFriendsRequest(@PathVariable(required = false) Optional<Long> lastId,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findFriendsRequests(Long.valueOf(userDetails.getUsername()), lastId));
    }

    @GetMapping("/invitations/code")
    public ResponseEntity<Long> getFriendInvitationsCode(@RequestParam SharedPlatform platform, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findInvitationCode(platform,Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/invitations/info/{key}")
    public ResponseEntity<FriendRequestDto> getFriendInvitationsInfo(@PathVariable Long key, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findInvitationInfo(Long.valueOf(userDetails.getUsername()), key));
    }
}
