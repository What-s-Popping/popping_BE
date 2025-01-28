package com.popping.domain.pop.service;

import com.popping.data.block.service.BlockMemberService;
import com.popping.data.member.entity.Member;
import com.popping.data.member.service.MemberService;
import com.popping.data.pop.emotion.ActionState;
import com.popping.data.pop.entity.BaseActionState;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.PopActionState;
import com.popping.data.pop.service.PopActionStateService;
import com.popping.data.pop.service.PopReadService;
import com.popping.data.pop.service.PopService;
import com.popping.data.report.service.PopReportService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.member.service.ProfileService;
import com.popping.domain.pop.dto.PopActionMemberDto;
import com.popping.domain.pop.dto.PopDetailDto;
import com.popping.domain.pop.dto.PopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindPopService {
    private final PopService popService;
    private final FindImgService imgService;
    private final BlockMemberService blockMemberService;
    private final PopReportService popReportService;
    private final PopActionStateService popActionStateService;
    private final PopReadService popReadService;
    private final MemberService memberService;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
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

    @Transactional
    public PopDetailDto.Response findPopDetail(Long popPk, Long requesterPk) {
        Pop pop = popService.findPop(popPk);
        Member requester = memberService.findMember(requesterPk);
        popReadService.readPop(pop, requester);

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

    public PopActionMemberDto.Response findPopActionMembers(Long popPk, Long requesterPk) {

        if (!popService.findPop(popPk).getWriter().getPk().equals(requesterPk)) {
            throw new IllegalArgumentException("작성자만 팝의 활동을 조회할 수 있습니다.");
        }
        List<PopActionMemberDto.MemberInfo> memberInfos = popActionStateService.findActions(popPk)
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
                                .toList().get(0)
                                .getActionState())
                        .build()
                ).toList();
        return PopActionMemberDto.Response.builder().memberInfos(memberInfos).build();
    }
}
