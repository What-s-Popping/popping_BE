package com.popping.data.pop.service;

import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.repository.RePopRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
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

    public List<RePop> findNotExpiredFriendRePops(List<Long> blockMemberPks,
                                                  List<Long> reportRePopPks,
                                                  Optional<Long> lastPk,
                                                  Long requesterPk) {
        PageRequest pageRequest = PageRequest.ofSize(MAX_SIZE);
        return rePopRepository.findNotExpiredFriendRePops(lastPk.orElse(null), requesterPk, reportRePopPks, blockMemberPks, pageRequest);
    }

    public RePop findRePop(Long rePopPk) {
        return rePopRepository.findById(rePopPk)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.RE_POP_NOT_FOUND.getMessage()));
    }
}
