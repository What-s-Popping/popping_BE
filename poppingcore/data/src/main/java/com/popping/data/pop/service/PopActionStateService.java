package com.popping.data.pop.service;

import com.popping.data.pop.entity.PopActionState;
import com.popping.data.pop.repository.PopActionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopActionStateService {
    private final PopActionStateRepository popActionStateRepository;

    public void save(PopActionState popActionState) {
        popActionStateRepository.save(popActionState);
    }

    public Optional<PopActionState> findImgSavedState(Long memberPk, Long popPk) {
        return popActionStateRepository.findImgSavedState(memberPk, popPk);
    }

    public Optional<PopActionState> findSharedState(Long memberPk, Long popPk) {
        return popActionStateRepository.findSharedState(memberPk, popPk);
    }

    public Optional<PopActionState> findRePopState(Long memberPk, Long popPk) {
        return popActionStateRepository.findRePopState(memberPk, popPk);
    }

    public Optional<PopActionState> findEmotionState(Long memberPk, Long popPk) {
        return popActionStateRepository.findEmotionState(memberPk, popPk);
    }
}
