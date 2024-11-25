package com.popping.domain.report.controller;

import com.popping.domain.report.dto.ReportDto;
import com.popping.domain.report.service.SaveReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final SaveReportService saveReportService;

    @PostMapping("/pops/{popPk}")
    public ResponseEntity<Void> savePopReport(@PathVariable Long popPk,
                                              @RequestBody ReportDto.Request reportDto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        saveReportService.savePopReport(popPk, Long.valueOf(userDetails.getUsername()), reportDto);

        return ResponseEntity.created(URI.create(""))
                .build();
    }

    @PostMapping("/re-pops/{rePopPk}")
    public ResponseEntity<Void> saveRePopReport(@PathVariable Long rePopPk,
                                                @RequestBody ReportDto.Request reportDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        saveReportService.saveRePopReport(rePopPk, Long.valueOf(userDetails.getUsername()), reportDto);

        return ResponseEntity.created(URI.create(""))
                .build();
    }
}
