package com.popping.data.pop.entity;

import com.popping.data.friendgroup.entity.CommonGroup;
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
public class Pop extends BasePop {
    @Column
    private boolean isGlobalShared;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharedGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommonGroup sharedGroup;

    @Builder
    public Pop(String chip, ColorChip colorChip, String contents, String imgName, boolean isPrivateProfile, Member writer, boolean isGlobalShared, CommonGroup sharedGroup, boolean isWidgetAllow) {
        super(chip, colorChip, contents, imgName, isPrivateProfile, writer, isWidgetAllow);
        this.isGlobalShared = isGlobalShared;
        this.sharedGroup = sharedGroup;
    }
}
