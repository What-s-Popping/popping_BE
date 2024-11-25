package com.popping.domain.pop.service;

import com.popping.data.pop.service.PopService;
import com.popping.domain.img.service.FindImgService;
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

    public List<PopDto.Response> findFriendPops(Optional<Long> lastPk, Long memberPk) {
        return popService.findFriendPops(lastPk, memberPk).stream()
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
}
