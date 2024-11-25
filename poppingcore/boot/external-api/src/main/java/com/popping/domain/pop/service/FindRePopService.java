package com.popping.domain.pop.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.pop.service.RePopService;
import com.popping.data.report.service.RePopReportService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.pop.dto.RePopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindRePopService {
    private final RePopService rePopService;
    private final FindImgService findImgService;
    private final RePopReportService rePopReportService;
    private final BlockMemberService blockMemberService;

    public List<RePopDto.Response> findNotExpiredFriendRePops(Optional<Long> lastPk, Long requesterPk) {
        List<Long> blockMemberPks = blockMemberService.findBlockMembers(requesterPk);
        List<Long> reportRePopPks = rePopReportService.findNotExpiredReportRePopPks(requesterPk);
        return rePopService.findNotExpiredFriendRePops(blockMemberPks, reportRePopPks, lastPk, requesterPk).stream()
                .map(rePop -> RePopDto.Response.builder()
                        .id(rePop.getPk())
                        .nickname(rePop.getWriter().getName())
                        .profileImgUrl(findImgService.generateProfileImgDownloadUrl(rePop.getWriter().getProfileImgFileName()))
                        .popImgUrl(findImgService.generateRePopImgDownloadUrl(rePop.getImgName()))
                        .chip(rePop.getChip())
                        .contents(rePop.getContents())
                        .colorChip(rePop.getColorChip())
                        .build()
                ).toList();
    }
}
