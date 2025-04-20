package com.popping.domain.widget.controller;

import com.popping.domain.widget.dto.WidgetDto;
import com.popping.domain.widget.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/widget")
public class WidgetController {
    private final WidgetService widgetService;

    @GetMapping("")
    public ResponseEntity<?> getRecentWidget(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<WidgetDto.Response> recentWidgetOp = widgetService.findRecentWidget(Long.valueOf(userDetails.getUsername()));

        if (recentWidgetOp.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(recentWidgetOp.get());
    }
}
