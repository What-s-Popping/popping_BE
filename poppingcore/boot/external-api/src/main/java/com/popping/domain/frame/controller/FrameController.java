package com.popping.domain.frame.controller;

import com.popping.domain.frame.dto.FrameDto;
import com.popping.domain.frame.service.FindFrameService;
import com.popping.global.responseform.ResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FrameController {
    private final FindFrameService findFrameService;

    @GetMapping("/frame")
    public ResponseEntity<ResponseForm<FrameDto.Response>> findDisplayedFrame() {
        return ResponseEntity.ok(
                ResponseForm.<FrameDto.Response>builder()
                        .httpStatus(HttpStatus.OK)
                        .responseMessage("Display frame retrieve successfully")
                        .content(findFrameService.findDisplayedFrame())
                        .build()
        );
    }
}
