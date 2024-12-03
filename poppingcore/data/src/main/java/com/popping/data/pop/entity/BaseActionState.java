package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.emotion.ActionState;
import com.popping.global.entity.BaseEntity;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseActionState extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ActionState actionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    public BaseActionState(ActionState actionState, Member member) {
        this.actionState = actionState;
        this.member = member;
    }

    public void updateEmotionState(ActionState actionState) {
        if (actionState.isNotEmotionState()) {
            throw new IllegalStateException(ExceptionMessage.IS_NOT_EMOTION_STATE.getMessage());
        }

        this.actionState = actionState;
    }
}
