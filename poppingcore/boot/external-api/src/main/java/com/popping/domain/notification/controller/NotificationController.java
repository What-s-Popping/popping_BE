package com.popping.domain.notification.controller;

import com.popping.domain.notification.dto.NotificationStatusDto;
import com.popping.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/status")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<NotificationStatusDto.Response> findAllowAllNotify(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.findAllowAllNotify(Long.valueOf(userDetails.getUsername())));
    }

    @PatchMapping("/all")
    public ResponseEntity<Void> updateAllowAllNotify(@RequestBody NotificationStatusDto.Request request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        notificationService.updateAllNotify(request, Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pop")
    public ResponseEntity<NotificationStatusDto.Response> findAllowPopNotify(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.findAllowPopNotify(Long.valueOf(userDetails.getUsername())));
    }

    @PatchMapping("/pop")
    public ResponseEntity<Void> updateAllowPopNotify(@RequestBody NotificationStatusDto.Request request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        notificationService.updateAllowPopNotify(request, Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.noContent().build();
    }
}
