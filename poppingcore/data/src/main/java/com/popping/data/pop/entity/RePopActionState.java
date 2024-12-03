package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.emotion.ActionState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RePopActionState extends BaseActionState {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rePop", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RePop rePop;

    @Builder
    public RePopActionState(ActionState actionState, Member member, RePop rePop) {
        super(actionState, member);
        this.rePop = rePop;
    }
}
