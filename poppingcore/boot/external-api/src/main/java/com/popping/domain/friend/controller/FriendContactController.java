package com.popping.domain.friend.controller;

import com.popping.domain.friend.dto.SaveFriendContactDto;
import com.popping.domain.friend.service.SaveFriendContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends/contact")
public class FriendContactController {
    private final SaveFriendContactService saveFriendContactService;

    @PostMapping("")
    public ResponseEntity<Void> saveFriendContact(@RequestBody SaveFriendContactDto.Request request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        saveFriendContactService.saveFriendContacts(Long.valueOf(userDetails.getUsername()), request);

        return ResponseEntity.created(URI.create("")).build();
    }
}
