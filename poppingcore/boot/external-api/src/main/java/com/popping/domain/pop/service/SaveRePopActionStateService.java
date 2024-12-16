package com.popping.domain.pop.service;

import com.popping.data.member.service.MemberService;
import com.popping.data.pop.emotion.ActionState;
import com.popping.data.pop.entity.RePopActionState;
import com.popping.data.pop.service.RePopActionStateService;
import com.popping.data.pop.service.RePopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveRePopActionStateService {
    private final RePopActionStateService rePopActionStateService;
    private final RePopService rePopService;
    private final MemberService memberService;

    @Transactional
    public void saveOrUpdateEmotion(Long memberPk, Long rePopPk, ActionState actionState) {
        Optional<RePopActionState> emotionState = rePopActionStateService.findEmotionState(memberPk, rePopPk);

        if (emotionState.isPresent()) {
            emotionState.get().updateEmotionState(actionState);
            return;
        }

        rePopActionStateService.save(
                RePopActionState.builder()
                        .member(memberService.findMember(memberPk))
                        .rePop(rePopService.findRePop(rePopPk))
                        .actionState(actionState)
                        .build()
        );
    }

    @Transactional
    public void saveImgSaveAction(Long memberPk, Long rePopPk) {
        boolean isNotExistImgSavedState = rePopActionStateService.isNotExistImgSavedState(memberPk, rePopPk);
        saveActionState(isNotExistImgSavedState, ActionState.IMG_SAVED, memberPk, rePopPk);
    }

    @Transactional
    public void saveRePopAction(Long memberPk, Long rePopPk) {
        boolean isNotExistRePopState = rePopActionStateService.isNotExistRePopState(memberPk, rePopPk);
        saveActionState(isNotExistRePopState, ActionState.RE_POP, memberPk, rePopPk);
    }

    @Transactional
    public void saveShareAction(Long memberPk, Long rePopPk) {
        boolean isNotExistSharedState = rePopActionStateService.isNotExistSharedState(memberPk, rePopPk);
        saveActionState(isNotExistSharedState, ActionState.SHARED, memberPk, rePopPk);
    }

    private void saveActionState(boolean isNotExistActionState, ActionState actionState, Long memberPk, Long rePopPk) {
        if (isNotExistActionState) {
            rePopActionStateService.save(
                    RePopActionState.builder()
                            .member(memberService.findMember(memberPk))
                            .rePop(rePopService.findRePop(rePopPk))
                            .actionState(actionState)
                            .build()
            );
        }
    }
}
