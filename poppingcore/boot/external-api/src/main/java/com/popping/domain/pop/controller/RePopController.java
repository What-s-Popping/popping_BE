package com.popping.domain.pop.controller;

import com.popping.domain.pop.dto.RePopDetailDto;
import com.popping.domain.pop.dto.RePopDto;
import com.popping.domain.pop.service.FindRePopService;
import com.popping.domain.pop.service.SaveRePopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/re-pops")
public class RePopController {
    private final FindRePopService findRePopService;
    private final SaveRePopService saveRePopService;

    @GetMapping("")
    public ResponseEntity<List<RePopDto.Response>> findNotExpiredFriendRePops(@RequestParam(required = false) Optional<Long> lastId,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findRePopService.findNotExpiredFriendRePops(lastId, Long.valueOf(userDetails.getUsername())));
    }

    @PostMapping("")
    public ResponseEntity<RePopDto.Response> saveRePop(@RequestBody RePopDto.Request requestDto,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        saveRePopService.save(Long.valueOf(userDetails.getUsername()), requestDto);

        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("/{rePopPk}")
    public ResponseEntity<RePopDetailDto.Response> findDetailPop(@PathVariable Long rePopPk,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findRePopService.findRePopDetail(rePopPk, Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/{rePopPk}/action")
    public ResponseEntity<?> findPopActionMembers(@PathVariable Long rePopPk,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findRePopService.findRePopActionMembers(rePopPk, Long.valueOf(userDetails.getUsername())));
    }
}
