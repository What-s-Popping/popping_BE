package com.popping.domain.pop.controller;

import com.popping.data.pop.chip.Chip;
import com.popping.domain.pop.dto.ChipDto;
import com.popping.domain.pop.dto.PopDto;
import com.popping.domain.pop.service.FindPopService;
import com.popping.domain.pop.service.SavePopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/pops")
@RequiredArgsConstructor
public class PopController {
    private final FindPopService findPopService;
    private final SavePopService savePopService;

    @PostMapping
    public ResponseEntity<?> savePosts(@RequestBody PopDto.Request request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info(request.toString());
        savePopService.savePop(request,Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chips")
    public ResponseEntity<ChipDto.Response> getChips() {
        return ResponseEntity.ok(ChipDto.Response.builder().chips(Chip.values()).build());
    }

    @GetMapping(value = {"", "/{lastId}"})
    public ResponseEntity<List<PopDto.Response>> getFriends(@PathVariable(required = false) Optional<Long> lastId,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(findPopService.findFriendPops(lastId, Long.valueOf(userDetails.getUsername())));
    }
}
