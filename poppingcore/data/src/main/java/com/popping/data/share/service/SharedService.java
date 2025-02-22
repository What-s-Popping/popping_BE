package com.popping.data.share.service;

import com.popping.data.share.entity.Shared;
import com.popping.data.share.entity.SharedPlatform;
import com.popping.data.share.entity.SharedType;
import com.popping.data.share.repository.SharedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SharedService {
    private final SharedRepository sharedRepository;

    public Optional<Shared> findShared(SharedPlatform platform, SharedType sharedType, Long requesterPk) {
        return sharedRepository.findShared(platform, sharedType, requesterPk);
    }

    public Shared findShared(Long pk) {
        return sharedRepository.findById(pk).orElseThrow(() -> new IllegalArgumentException("공유 링크 생성 기록을 찾을 수 없습니다."));
    }

    public void saveShared(Shared shared) {
        sharedRepository.save(shared);
    }
}
