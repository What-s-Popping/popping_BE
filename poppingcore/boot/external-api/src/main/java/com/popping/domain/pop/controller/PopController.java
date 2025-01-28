package com.popping.domain.pop.controller;

import com.popping.data.pop.chip.Chip;
import com.popping.domain.pop.dto.ChipDto;
import com.popping.domain.pop.dto.PopDetailDto;
import com.popping.domain.pop.dto.PopDto;
import com.popping.domain.pop.service.FindPopService;
import com.popping.domain.pop.service.SavePopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pops")
@RequiredArgsConstructor
public class PopController {
    private final FindPopService findPopService;
    private final SavePopService savePopService;

    @PostMapping
    public ResponseEntity<?> savePosts(@RequestBody PopDto.Request request, @AuthenticationPrincipal UserDetails userDetails) {
        savePopService.savePop(request,Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chips")
    public ResponseEntity<ChipDto.Response> getChips() {
        return ResponseEntity.ok(ChipDto.Response.builder().chips(Chip.values()).build());
    }

    @GetMapping("")
    public ResponseEntity<List<PopDto.Response>> findNotExpiredFriendPops(@RequestParam(required = false) Optional<Long> lastId,
                                                                          @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findPopService.findNotExpiredFriendPops(lastId, Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/{popPk}")
    public ResponseEntity<PopDetailDto.Response> findDetailPop(@PathVariable Long popPk,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findPopService.findPopDetail(popPk, Long.valueOf(userDetails.getUsername())));
    }

    @GetMapping("/{popPk}/action")
    public ResponseEntity<?> findPopActionMembers(@PathVariable Long popPk,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findPopService.findPopActionMembers(popPk, Long.valueOf(userDetails.getUsername())));
    }
}
