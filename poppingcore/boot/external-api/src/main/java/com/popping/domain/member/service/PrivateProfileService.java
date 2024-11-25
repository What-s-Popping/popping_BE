package com.popping.domain.member.service;

import com.popping.data.pop.service.PopService;
import com.popping.domain.member.dto.PrivateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrivateProfileService {
    private final PopService popService;

    public PrivateProfile.QuotaResponse findTodayPrivateProfileQuota(Long memberPk) {
        LocalDateTime lastPrivateProfilePostDate = popService.findLastPrivateProfilePopDate(memberPk);
        return PrivateProfile.QuotaResponse.builder().privateProfileQuota(
                (lastPrivateProfilePostDate != null && lastPrivateProfilePostDate.isBefore(LocalDateTime.now().minusHours(24))) ?
                        1 : 0
        ).build();
    }

    public PrivateProfile.PrivatePostCntResponse findPrivateProfilePostCnt(Long memberPk) {
        return PrivateProfile.PrivatePostCntResponse.builder()
                .privateProfilePostCnt(popService.findTotalCntPrivateProfilePop(memberPk))
                .build();
    }
}
