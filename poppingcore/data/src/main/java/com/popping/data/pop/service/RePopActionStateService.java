package com.popping.data.pop.service;

import com.popping.data.pop.entity.PopActionState;
import com.popping.data.pop.entity.RePopActionState;
import com.popping.data.pop.repository.RePopActionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RePopActionStateService {
    private final RePopActionStateRepository rePopActionStateRepository;

    public void save(RePopActionState rePopActionState) {
        rePopActionStateRepository.save(rePopActionState);
    }

    public boolean isNotExistImgSavedState(Long memberPk, Long popPk) {
        return rePopActionStateRepository.findImgSavedState(memberPk, popPk).isEmpty();
    }

    public boolean isNotExistSharedState(Long memberPk, Long popPk) {
        return rePopActionStateRepository.findSharedState(memberPk, popPk).isEmpty();
    }

    public boolean isNotExistRePopState(Long memberPk, Long popPk) {
        return rePopActionStateRepository.findRePopState(memberPk, popPk).isEmpty();
    }

    public Optional<RePopActionState> findEmotionState(Long memberPk, Long popPk) {
        return rePopActionStateRepository.findEmotionState(memberPk, popPk);
    }

    public List<RePopActionState> findActions(Long popPk) {
        return rePopActionStateRepository.findActions(popPk);
    }
}
