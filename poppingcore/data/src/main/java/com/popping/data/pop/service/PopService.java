package com.popping.data.pop.service;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.repository.PopRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
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

    public List<Pop> findNotExpiredPops(List<Long> blockMemberPks,
                                        List<Long> reportPopPks,
                                        Optional<Long> lastPk,
                                        Long requesterPk) {
        PageRequest pageRequest = PageRequest.ofSize(MAX_SIZE);
        return popRepository.findNotExpiredFriendPops(lastPk.orElse(null), requesterPk, reportPopPks, blockMemberPks, pageRequest);
    }

    public Pop findPop(Long popPk) {
        return popRepository.findById(popPk)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.POP_NOT_FOUND.getMessage()));
    }

    public List<Member> findNotExpiredFriends(Optional<Long> lastFriendPk, Long requesterPk, List<Long> blockedMemberPks) {
        PageRequest pageRequest = PageRequest.ofSize(15);
        return popRepository.findNotExpiredPopFriends(lastFriendPk.orElse(null), requesterPk, blockedMemberPks, pageRequest);
    }

    public List<Pop> findNotExpiredPops(List<Long> reportPopPks, List<Member> friends) {
        return popRepository.findNotExpiredFriendPopPks(reportPopPks, friends);
    }
}
