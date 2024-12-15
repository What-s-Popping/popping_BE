package com.popping.domain.pop.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.pop.entity.BaseActionState;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.service.PopActionStateService;
import com.popping.data.pop.service.PopService;
import com.popping.data.report.service.PopReportService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.pop.dto.PopDetailDto;
import com.popping.domain.pop.dto.PopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPopService {
    private final PopService popService;
    private final FindImgService imgService;
    private final BlockMemberService blockMemberService;
    private final PopReportService popReportService;
    private final PopActionStateService popActionStateService;

    public List<PopDto.Response> findNotExpiredFriendPops(Optional<Long> lastPk, Long requesterPk) {
        List<Long> blockMemberPks = blockMemberService.findBlockMembers(requesterPk);
        List<Long> reportPopPks = popReportService.findNotExpiredReportPopPks(requesterPk);
        return popService.findNotExpiredPops(blockMemberPks, reportPopPks, lastPk, requesterPk).stream()
                .map(pop -> PopDto.Response.builder()
                        .id(pop.getPk())
                        .nickname(pop.getWriter().getName())
                        .profileImgUrl(imgService.generateProfileImgDownloadUrl(pop.getWriter().getProfileImgFileName()))
                        .popImgUrl(imgService.generatePopImgDownloadUrl(pop.getImgName()))
                        .chip(pop.getChip())
                        .colorChip(pop.getColorChip())
                        .contents(pop.getContents())
                    .build())
                .toList();
    }

    public PopDetailDto.Response findPopDetail(Long popPk, Long requesterPk) {
        Pop pop = popService.findPop(popPk);
        return PopDetailDto.Response.builder()
                .popId(pop.getPk())
                .popImgUrl(imgService.generatePopImgDownloadUrl(pop.getImgName()))
                .chip(pop.getChip())
                .colorChip(pop.getColorChip())
                .contents(pop.getContents())
                .createAt(pop.getCreatedAt())
                .actionState(popActionStateService.findEmotionState(requesterPk, popPk)
                        .map(BaseActionState::getActionState)
                        .orElse(null))
                .isWriter(pop.getWriter().isWriter(requesterPk))
                .writerId(pop.getWriter().getPk())
                .nickname(pop.getWriter().getName())
                .profileImgUrl(imgService.generateProfileImgDownloadUrl(pop.getWriter().getProfileImgFileName()))
                .build();
    }
}
