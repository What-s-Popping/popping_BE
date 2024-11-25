package com.popping.domain.frame.controller;

import com.popping.domain.frame.dto.FrameDto;
import com.popping.domain.frame.service.FindFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FrameController {
    private final FindFrameService findFrameService;

    @GetMapping("/frame")
    public ResponseEntity<FrameDto.Response> findDisplayedFrame() {
        return ResponseEntity.ok(findFrameService.findDisplayedFrame());
    }
}
