package com.popping.data.post.service;

import com.popping.data.post.entity.Pop;
import com.popping.data.post.repository.PopRepository;
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
        PageRequest pageRequest = PageRequest.ofSize(50);
        return popRepository.findMyPopNextPage(lastIdx.orElse(null), memberPk, pageRequest);
    }
}
