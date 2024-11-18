package com.popping.domain.member.controller;

import com.popping.domain.member.dto.PopcornDto;
import com.popping.domain.member.dto.PrivateProfile;
import com.popping.domain.member.service.PopcornService;
import com.popping.domain.member.service.PrivateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final PopcornService popcornService;
    private final PrivateProfileService privateProfileService;

    @GetMapping("/popcorn/balance")
    public ResponseEntity<PopcornDto.Response> getPopcornBalance(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                popcornService.getPopcornBalance(Long.valueOf(userDetails.getUsername()))
        );
    }

    @GetMapping("/private-profile/quota")
    public ResponseEntity<PrivateProfile.QuotaResponse> getPrivateProfileQuota(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            privateProfileService.findTodayPrivateProfileQuota(Long.valueOf(userDetails.getUsername()))
        );
    }

    @GetMapping("/pops/private-profile/count")
    public ResponseEntity<?> getPrivateProfilePostCnt(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                privateProfileService.findPrivateProfilePostCnt(Long.valueOf(userDetails.getUsername()))
        );
    }

}
