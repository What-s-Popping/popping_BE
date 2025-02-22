package com.popping.data.share.entity;

import com.popping.data.member.entity.Member;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shared extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member sharedMember;

    @Enumerated(EnumType.STRING)
    SharedType sharedType;

    @Enumerated(EnumType.STRING)
    SharedPlatform sharedPlatform;

    @Builder
    public Shared(Member sharedMember, SharedType sharedType, SharedPlatform sharedPlatform) {
        this.sharedMember = sharedMember;
        this.sharedType = sharedType;
        this.sharedPlatform = sharedPlatform;
    }
}
