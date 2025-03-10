package com.popping.domain.pop.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.pop.emotion.ActionState;
import com.popping.data.pop.entity.BaseActionState;
import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.service.RePopActionStateService;
import com.popping.data.pop.service.RePopService;
import com.popping.data.report.service.RePopReportService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.member.service.ProfileService;
import com.popping.domain.pop.dto.PopActionMemberDto;
import com.popping.domain.pop.dto.RePopDetailDto;
import com.popping.domain.pop.dto.RePopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindRePopService {
    private final RePopService rePopService;
    private final FindImgService findImgService;
    private final RePopReportService rePopReportService;
    private final BlockMemberService blockMemberService;
    private final FindImgService imgService;
    private final RePopActionStateService rePopActionStateService;
    private final ProfileService profileService;

    public List<RePopDto.Response> findNotExpiredFriendRePops(Optional<Long> lastPk, Long requesterPk) {
        List<Long> blockMemberPks = blockMemberService.findBlockMembers(requesterPk);
        List<Long> reportRePopPks = rePopReportService.findNotExpiredReportRePopPks(requesterPk);
        return rePopService.findNotExpiredFriendRePops(blockMemberPks, reportRePopPks, lastPk, requesterPk).stream()
                .map(rePop -> RePopDto.Response.builder()
                        .id(rePop.getPk())
                        .nickname(rePop.getWriterName())
                        .profileImgUrl(findImgService.generateProfileImgDownloadUrl(rePop.getWriter().getProfileImgFileName(), rePop.isPrivateProfile()))
                        .popImgUrl(findImgService.generateRePopImgDownloadUrl(rePop.getImgName()))
                        .chip(rePop.getChip())
                        .contents(rePop.getContents())
                        .colorChip(rePop.getColorChip())
                        .build()
                ).toList();
    }

    public RePopDetailDto.Response findRePopDetail(Long popPk, Long requesterPk) {
        RePop rePop = rePopService.findRePop(popPk);
        return RePopDetailDto.Response.builder()
                .popId(rePop.getPk())
                .popImgUrl(imgService.generateRePopImgDownloadUrl(rePop.getImgName()))
                .chip(rePop.getChip())
                .colorChip(rePop.getColorChip())
                .contents(rePop.getContents())
                .createAt(rePop.getCreatedAt())
                .actionState(rePopActionStateService.findEmotionState(requesterPk, popPk)
                        .map(BaseActionState::getActionState)
                        .orElse(null))
                .isWriter(rePop.getWriter().isWriter(requesterPk))
                .writerId(rePop.getWriter().getPk())
                .nickname(rePop.getWriterName())
                .profileImgUrl(imgService.generateProfileImgDownloadUrl(rePop.getWriter().getProfileImgFileName(), rePop.isPrivateProfile()))
                .build();
    }

    public PopActionMemberDto.Response findRePopActionMembers(Long rePopPk, Long requesterPk) {
        if (!rePopService.findRePop(rePopPk).getWriter().getPk().equals(requesterPk)) {
            throw new IllegalArgumentException("작성자만 리팝의 활동을 조회할 수 있습니다.");
        }
        List<PopActionMemberDto.MemberInfo> memberInfos = rePopActionStateService.findActions(rePopPk)
                .stream()
                .collect(Collectors.groupingBy(BaseActionState::getMember)).entrySet()
                .stream()
                .map(entry -> PopActionMemberDto.MemberInfo.builder()
                        .memberId(entry.getKey().getPk())
                        .memberName(entry.getKey().getName())
                        .memberProfileImgUrl(profileService.findNameAndProfileImg(entry.getKey().getPk())
                                .getProfileImgUrl())
                        .isShared(entry.getValue().stream()
                                .anyMatch(popActionState -> popActionState
                                        .getActionState()
                                        .equals(ActionState.SHARED)))
                        .isSaved(entry.getValue().stream()
                                .anyMatch(popActionState -> popActionState
                                        .getActionState()
                                        .equals(ActionState.IMG_SAVED)))
                        .isRepop(entry.getValue().stream()
                                .anyMatch(popActionState -> popActionState
                                        .getActionState()
                                        .equals(ActionState.RE_POP)))
                        .actionState(entry.getValue().stream()
                                .filter(popActionState -> !popActionState
                                        .getActionState()
                                        .isNotEmotionState())
                                .findFirst()
                                .map(BaseActionState::getActionState)
                                .orElse(null)
                        )
                        .build()
                ).toList();
        return PopActionMemberDto.Response.builder().memberInfos(memberInfos).build();
    }
}
