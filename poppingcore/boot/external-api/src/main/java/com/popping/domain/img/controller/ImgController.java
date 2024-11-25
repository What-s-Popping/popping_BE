package com.popping.domain.img.controller;

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

    @GetMapping("/posts/upload")
    public ResponseEntity<?> getS3UploadUrl() {
        return ResponseEntity.ok(saveImgService.generateUploadUrl());
    }
}
