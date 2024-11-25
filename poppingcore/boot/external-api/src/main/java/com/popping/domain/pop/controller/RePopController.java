package com.popping.domain.pop.controller;

import com.popping.domain.pop.dto.RePopDto;
import com.popping.domain.pop.service.FindRePopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/re-pops")
public class RePopController {
    private final FindRePopService findRePopService;

    @GetMapping(value = {"", "/{lastId}"})
    public ResponseEntity<List<RePopDto.Response>> findNotExpiredFriendRePops(@PathVariable(required = false) Optional<Long> lastId,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findRePopService.findNotExpiredFriendRePops(lastId, Long.valueOf(userDetails.getUsername())));
    }
}
