package com.popping.domain.pop.service;

import com.popping.data.member.service.MemberService;
import com.popping.data.pop.emotion.ActionState;
import com.popping.data.pop.entity.PopActionState;
import com.popping.data.pop.service.PopActionStateService;
import com.popping.data.pop.service.PopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavePopActionStateService {
    private final PopActionStateService popActionStateService;
    private final PopService popService;
    private final MemberService memberService;

    @Transactional
    public void saveOrUpdateEmotion(Long memberPk, Long popPk, ActionState actionState) {
        Optional<PopActionState> emotionState = popActionStateService.findEmotionState(memberPk, popPk);

        if (emotionState.isPresent()) {
            emotionState.get().updateEmotionState(actionState);
            return;
        }

        popActionStateService.save(
                PopActionState.builder()
                        .member(memberService.findMember(memberPk))
                        .pop(popService.findPop(popPk))
                        .actionState(actionState)
                        .build()
        );
    }

    @Transactional
    public void saveImgSaveAction(Long memberPk, Long popPk) {
        Optional<PopActionState> sharedState = popActionStateService.findImgSavedState(memberPk, popPk);

        saveOrUpdateTime(sharedState, ActionState.IMG_SAVED, memberPk, popPk);
    }

    @Transactional
    public void saveRePopAction(Long memberPk, Long popPk) {
        Optional<PopActionState> sharedState = popActionStateService.findRePopState(memberPk, popPk);

        saveOrUpdateTime(sharedState, ActionState.RE_POP, memberPk, popPk);
    }

    @Transactional
    public void saveShareAction(Long memberPk, Long popPk) {
        Optional<PopActionState> sharedState = popActionStateService.findSharedState(memberPk, popPk);

        saveOrUpdateTime(sharedState, ActionState.SHARED, memberPk, popPk);
    }

    private void saveOrUpdateTime(Optional<PopActionState> popActionState, ActionState actionState, Long memberPk, Long popPk) {
        if (popActionState.isPresent()) {
            popActionState.get().updateCreatedAt();
            return;
        }

        popActionStateService.save(
                PopActionState.builder()
                        .member(memberService.findMember(memberPk))
                        .pop(popService.findPop(popPk))
                        .actionState(actionState)
                        .build()
        );
    }
}
