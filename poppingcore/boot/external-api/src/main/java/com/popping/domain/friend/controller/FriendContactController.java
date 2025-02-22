package com.popping.domain.friend.controller;

import com.popping.domain.friend.dto.FindFriendContactDto;
import com.popping.domain.friend.dto.SaveFriendContactDto;
import com.popping.domain.friend.service.FindFriendContactService;
import com.popping.domain.friend.service.SaveFriendContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends/contact")
public class FriendContactController {
    private final SaveFriendContactService saveFriendContactService;
    private final FindFriendContactService findFriendContactService;

    @PostMapping("")
    public ResponseEntity<Void> saveFriendContact(@RequestBody SaveFriendContactDto.Request request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        saveFriendContactService.saveFriendContacts(Long.valueOf(userDetails.getUsername()), request);

        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("")
    public ResponseEntity<List<FindFriendContactDto.Response>> findFriendsMayKnow(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findFriendContactService.findFriendsMayKnow(Long.valueOf(userDetails.getUsername())));
    }
}
