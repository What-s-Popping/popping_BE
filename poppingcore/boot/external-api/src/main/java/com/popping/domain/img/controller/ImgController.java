package com.popping.domain.img.controller;

import com.popping.domain.img.dto.ImgDto;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.img.service.SaveImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imgs")
@RequiredArgsConstructor
public class ImgController {
    private final SaveImgService saveImgService;
    private final FindImgService findImgService;

    @GetMapping("/posts/upload")
    public ResponseEntity<?> getS3UploadUrl() {
        return ResponseEntity.ok(saveImgService.generatePopUploadUrl());
    }

    @GetMapping("/re-pops/upload")
    public ResponseEntity<ImgDto.Response> findRePopS3UploadUrl() {
        return ResponseEntity.ok(saveImgService.generateRePopUploadUrl());
    }
}
