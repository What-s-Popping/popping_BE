package com.popping.domain.block.controller;

import com.popping.domain.block.service.DeleteBlockMemberService;
import com.popping.domain.block.service.FindBlockMemberService;
import com.popping.domain.block.dto.BlockDto;
import com.popping.domain.block.dto.BlockMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockMemberController {
    private final FindBlockMemberService findBlockMemberService;
    private final DeleteBlockMemberService deleteBlockMemberService;

    @GetMapping("/members/{toMemberPk}/existence")
    public ResponseEntity<BlockDto.Response> findExistBlockHistory(@PathVariable Long toMemberPk,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                findBlockMemberService.isExistBlockedHistory(toMemberPk, Long.valueOf(userDetails.getUsername()))
        );
    }

    @GetMapping("/members")
    public ResponseEntity<List<BlockMemberDto.Response>> findBlockMembers(@AuthenticationPrincipal UserDetails userDetails) {
        List<BlockMemberDto.Response> response = findBlockMemberService.findBlockMembers(Long.valueOf(userDetails.getUsername()));

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{toMemberPk}")
    public ResponseEntity<?> deleteBlockMember(@PathVariable Long toMemberPk,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        deleteBlockMemberService.deleteBlockMember(Long.valueOf(userDetails.getUsername()), toMemberPk);

        return ResponseEntity.noContent().build();
    }
}
