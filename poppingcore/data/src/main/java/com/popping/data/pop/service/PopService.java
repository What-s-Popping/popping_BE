package com.popping.data.pop.service;

import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.repository.PopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopService {
    private final PopRepository popRepository;
    private static final int MAX_SIZE = 50;

    public void save(Pop pop) {
        popRepository.save(pop);
    }

    public LocalDateTime findLastPrivateProfilePopDate(Long memberPk) {
        return popRepository.findLastPrivateProfilePostDate(memberPk);
    }

    public int findTotalCntPrivateProfilePop(Long memberPk) {
        return popRepository.findTotalCntPrivateProfilePop(memberPk);
    }

    public List<Pop> findMyPopNextPage(Optional<Long> lastIdx, Long memberPk) {
        PageRequest pageRequest = PageRequest.ofSize(MAX_SIZE);
        return popRepository.findMyPopNextPage(lastIdx.orElse(null), memberPk, pageRequest);
    }

    public List<Pop> findFriendPops(Optional<Long> lastPk, Long memberPk) {
        PageRequest pageRequest = PageRequest.ofSize(MAX_SIZE);
        return popRepository.findFriendPops(lastPk.orElse(null), memberPk, pageRequest);
    }
}
