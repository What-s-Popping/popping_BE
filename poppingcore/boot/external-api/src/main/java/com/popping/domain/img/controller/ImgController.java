package com.popping.domain.img.controller;

import com.popping.domain.img.service.ImgSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imgs")
@RequiredArgsConstructor
public class ImgController {
    private final ImgSaveService imgSaveService;

    @GetMapping("/posts/upload")
    public ResponseEntity<?> getS3UploadUrl() {
        return ResponseEntity.ok(imgSaveService.generateUploadUrl());
    }
}
