package com.popping.domain.pop.controller;

import com.popping.domain.pop.dto.EmotionDto;
import com.popping.domain.pop.service.SavePopActionStateService;
import com.popping.domain.pop.service.SaveRePopActionStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/re-pops")
public class RePopActionStateController {
    private final SaveRePopActionStateService saveRePopActionStateService;

    @PostMapping("{rePopPk}/actions/emotion")
    public ResponseEntity<Void> saveOrUpdateEmotion(@PathVariable Long rePopPk,
                                                    @RequestBody EmotionDto emotionDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        saveRePopActionStateService.saveOrUpdateEmotion(Long.valueOf(userDetails.getUsername()), rePopPk, emotionDto.getEmotion());

        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("{rePopPk}/actions/share")
    public ResponseEntity<Void> saveShareState(@PathVariable Long rePopPk,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        saveRePopActionStateService.saveShareAction(Long.valueOf(userDetails.getUsername()), rePopPk);

        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("{rePopPk}/actions/save")
    public ResponseEntity<Void> saveImgSaveState(@PathVariable Long rePopPk,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        saveRePopActionStateService.saveImgSaveAction(Long.valueOf(userDetails.getUsername()), rePopPk);

        return ResponseEntity.created(URI.create("")).build();
    }
}
