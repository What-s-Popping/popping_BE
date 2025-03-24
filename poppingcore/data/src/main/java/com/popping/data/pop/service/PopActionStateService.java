package com.popping.data.pop.service;

import com.popping.data.pop.entity.PopActionState;
import com.popping.data.pop.repository.PopActionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopActionStateService {
    private final PopActionStateRepository popActionStateRepository;

    public void save(PopActionState popActionState) {
        popActionStateRepository.save(popActionState);
    }

    public boolean isNotExistImgSavedState(Long memberPk, Long popPk) {
        return popActionStateRepository.findImgSavedState(memberPk, popPk).isEmpty();
    }

    public boolean isNotExistSharedState(Long memberPk, Long popPk) {
        return popActionStateRepository.findSharedState(memberPk, popPk).isEmpty();
    }

    public boolean isNotExistRePopState(Long memberPk, Long popPk) {
        return popActionStateRepository.findRePopState(memberPk, popPk).isEmpty();
    }

    public Optional<PopActionState> findEmotionState(Long memberPk, Long popPk) {
        return popActionStateRepository.findEmotionState(memberPk, popPk);
    }

    public List<PopActionState> findActions(Long popPk) {
        return popActionStateRepository.findActions(popPk);
    }

    public void deletePopActionState(long popWriter, long actionWriter) {
        popActionStateRepository.deletePopActionState(popWriter, actionWriter);
    }
}
