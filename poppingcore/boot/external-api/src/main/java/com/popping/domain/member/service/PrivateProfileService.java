package com.popping.domain.member.service;

import com.popping.data.pop.service.PopService;
import com.popping.domain.member.dto.PrivateProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateProfileService {
    private final PopService popService;

    public PrivateProfile.QuotaResponse findTodayPrivateProfileQuota(Long memberPk) {
        LocalDateTime lastPrivateProfilePostDate = popService.findTodayLastPrivateProfilePopDate(memberPk);
        log.info("{}", Objects.isNull(lastPrivateProfilePostDate));
        return PrivateProfile.QuotaResponse.builder().privateProfileQuota(
                lastPrivateProfilePostDate == null ? 1 : 0
        ).build();
    }

    public PrivateProfile.PrivatePostCntResponse findPrivateProfilePostCnt(Long memberPk) {
        return PrivateProfile.PrivatePostCntResponse.builder()
                .privateProfilePostCnt(popService.findTotalCntPrivateProfilePop(memberPk))
                .build();
    }
}
