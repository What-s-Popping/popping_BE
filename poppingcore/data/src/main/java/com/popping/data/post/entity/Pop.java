package com.popping.data.post.entity;

import com.popping.data.friendgroup.entity.CommonGroup;
import com.popping.data.member.entity.Member;
import com.popping.data.post.chip.ColorChip;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pop extends BaseEntity {

    @Column
    private boolean isPrivateProfile;
    @Column
    private boolean isGlobalShared;
    @Column
    private String chip;
    @Column
    @Enumerated(EnumType.STRING)
    private ColorChip colorChip;
    @Column
    private String contents;
    @Column
    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharedGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommonGroup sharedGroup;

    @Builder
    public Pop(boolean isPrivateProfile, boolean isGlobalShared, String chip, ColorChip colorChip, String contents, String imgName, Member writer, CommonGroup sharedGroup) {
        this.isPrivateProfile = isPrivateProfile;
        this.isGlobalShared = isGlobalShared;
        this.chip = chip;
        this.colorChip = colorChip;
        this.contents = contents;
        this.imgName = imgName;
        this.writer = writer;
        this.sharedGroup = sharedGroup;
    }
}
