package com.popping.domain.pop.service;

import com.popping.data.pop.service.RePopService;
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

    public List<RePopDto.Response> findFriendRePops(Optional<Long> lastPk, Long requesterPk) {
        return rePopService.findFriendRePops(lastPk, requesterPk).stream()
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
