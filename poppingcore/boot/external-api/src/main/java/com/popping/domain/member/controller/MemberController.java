package com.popping.domain.member.controller;

import com.popping.domain.member.dto.PopcornDto;
import com.popping.domain.member.dto.PrivateProfile;
import com.popping.domain.member.dto.ProfileDto;
import com.popping.domain.member.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final PopcornService popcornService;
    private final PrivateProfileService privateProfileService;
    private final ProfileService profileService;
    private final MyPopService myPopService;
    private final WithdrawMemberService withdrawMemberService;

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

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                profileService.findNameAndProfileImg(Long.valueOf(userDetails.getUsername()))
        );
    }

    @GetMapping(value = {"/pops","/pops/{lastIdx}"})
    public ResponseEntity<?> getMemberPops(@PathVariable(value = "lastIdx", required = false) Optional<Long> lastIdx,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                myPopService.findMyPopNextPage(lastIdx, Long.valueOf(userDetails.getUsername()))
        );
    }

    @GetMapping("/profile/img/upload-url")
    public ResponseEntity<?> getProfileImgUploadUrl(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                profileService.generateProfileImgUploadUrl(Long.valueOf(userDetails.getUsername()))
        );
    }

    @PatchMapping("/profile/name")
    public ResponseEntity<?> updateMemberName(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody ProfileDto.Request request) {
        profileService.updateProfileName(Long.valueOf(userDetails.getUsername()), request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/name")
    public ResponseEntity<?> getMemberName(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                profileService.findName(Long.valueOf(userDetails.getUsername()))
        );
    }

    @DeleteMapping("")
    public ResponseEntity<Void> withdrawMember(@AuthenticationPrincipal UserDetails userDetails) {
        withdrawMemberService.withdrawMember(Long.valueOf(userDetails.getUsername()));
        return ResponseEntity.noContent().build();
    }
}
