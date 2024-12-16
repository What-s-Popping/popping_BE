package com.popping.data.pop.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.chip.ColorChip;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BasePop extends BaseEntity {
    @Column
    private boolean isPrivateProfile;
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

    public BasePop(String chip, ColorChip colorChip, String contents, String imgName, boolean isPrivateProfile, Member writer) {
        this.chip = chip;
        this.colorChip = colorChip;
        this.contents = contents;
        this.imgName = imgName;
        this.isPrivateProfile = isPrivateProfile;
        this.writer = writer;
    }

    public boolean isWriter(Long writerPk) {
        return this.writer.getPk().equals(writerPk);
    }
}
