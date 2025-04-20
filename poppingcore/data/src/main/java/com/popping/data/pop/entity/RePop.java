package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.chip.ColorChip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RePop extends BasePop {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetMember", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member targetMember;

    @Builder
    public RePop(String chip, ColorChip colorChip, String contents, String imgName, boolean isPrivateProfile, Member writer, Member targetMember, boolean isWidgetAllow) {
        super(chip, colorChip, contents, imgName, isPrivateProfile, writer, isWidgetAllow);
        this.targetMember = targetMember;
    }
}
