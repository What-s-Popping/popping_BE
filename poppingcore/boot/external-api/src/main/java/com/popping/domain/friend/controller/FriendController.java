package com.popping.domain.friend.controller;

import com.popping.domain.friend.service.FindFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FindFriendService findFriendService;

    @GetMapping("/sharable")
    public ResponseEntity<?> getFriendsList(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendService.getFriendsForSharing(Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/existence")
    public ResponseEntity<?> getFriendExistence(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendService.getFriendExistence(Long.valueOf(userDetails.getUsername())));
    }
}
