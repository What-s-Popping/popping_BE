package com.popping.domain.pop.controller;

import com.popping.domain.pop.dto.EmotionDto;
import com.popping.domain.pop.service.SavePopActionStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pops")
public class PopActionStateController {
    private final SavePopActionStateService savePopActionStateService;

    @PostMapping("{popPk}/actions/emotion")
    public ResponseEntity<Void> saveOrUpdateEmotion(@PathVariable Long popPk,
                                                    @RequestBody EmotionDto emotionDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        savePopActionStateService.saveOrUpdateEmotion(Long.valueOf(userDetails.getUsername()), popPk, emotionDto.getEmotion());

        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("{popPk}/actions/share")
    public ResponseEntity<Void> saveShareState(@PathVariable Long popPk,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        savePopActionStateService.saveShareAction(Long.valueOf(userDetails.getUsername()), popPk);

        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("{popPk}/actions/save")
    public ResponseEntity<Void> saveImgSaveState(@PathVariable Long popPk,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        savePopActionStateService.saveImgSaveAction(Long.valueOf(userDetails.getUsername()), popPk);

        return ResponseEntity.created(URI.create("")).build();
    }
}
