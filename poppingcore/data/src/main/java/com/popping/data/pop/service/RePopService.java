package com.popping.data.pop.service;

import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.repository.RePopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RePopService {
    private final RePopRepository rePopRepository;
    private static final int MAX_SIZE = 50;

    public List<RePop> findFriendRePops(Optional<Long> lastPk, Long requesterPk) {
        PageRequest pageRequest = PageRequest.ofSize(MAX_SIZE);
        return rePopRepository.findFriendRePops(lastPk.orElse(null), requesterPk, pageRequest);
    }
}
