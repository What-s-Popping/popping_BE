package com.popping.domain.friend.controller;

import com.popping.data.share.entity.SharedPlatform;
import com.popping.domain.friend.dto.*;
import com.popping.domain.friend.service.FindFriendService;
import com.popping.domain.friend.service.FriendRequestService;
import com.popping.domain.pop.dto.FriendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<?> getNotExpiredPopFriends(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Long, FriendDto.Response> response = findFriendService.findNotExpiredPopFriends(Long.valueOf(userDetails.getUsername()));

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
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
        friendRequestService.friendsRequestAccept(toMemberPk.getToMemberPk(), Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/requests","/requests/{lastId}"})
    public ResponseEntity<FindFriendDto.Response> getFriendsRequest(@PathVariable(required = false) Optional<Long> lastId,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findFriendsRequests(Long.parseLong(userDetails.getUsername()), lastId));
    }

    @GetMapping("/invitations/code")
    public ResponseEntity<Long> getFriendInvitationsCode(@RequestParam SharedPlatform platform, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findInvitationCode(platform,Long.parseLong(userDetails.getUsername())));
    }

    @GetMapping("/invitations/info/{key}")
    public ResponseEntity<FriendRequestDto> getFriendInvitationsInfo(@PathVariable Long key) {
        return ResponseEntity.ok(friendRequestService.findInvitationInfo(key));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteFriend(@PathVariable long memberId, @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.deleteFriend(memberId, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getFriendGroups(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(friendRequestService.findCustomFriendsGroups(Long.parseLong(userDetails.getUsername())));
    }

    @PostMapping("/groups")
    public ResponseEntity<Void> saveFriendsGroup(@RequestBody SaveCustomFriendGroupDto request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.saveCustomFriendsGroup(request, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/groups/{groupId}/name")
    public ResponseEntity<Void> updateFriendsGroupName(@RequestBody UpdateCustomFriendGroupNameDto request,
                                                      @PathVariable long groupId,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.updateCustomFriendsGroupName(groupId, request, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<Void> deleteFriendsGroup(@PathVariable long groupId, @AuthenticationPrincipal UserDetails userDetails) {
        friendRequestService.deleteCustomFriendsGroup(groupId, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/groups/{groupId}/members/{memberId}")
    public ResponseEntity<Void> deleteFriendsGroupMember(
            @PathVariable long groupId,
            @PathVariable long memberId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        friendRequestService.deleteCustomFriendsGroupMember(groupId, memberId, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/groups/{groupId}/members/{memberId}")
    public ResponseEntity<Void> saveNewFriendsGroupMember(
            @PathVariable long groupId,
            @PathVariable long memberId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        friendRequestService.saveNewCustomFriendsGroupMember(groupId, memberId, Long.parseLong(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }
}
