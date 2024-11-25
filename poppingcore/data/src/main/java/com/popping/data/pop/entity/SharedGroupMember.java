package com.popping.data.pop.entity;

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
public class SharedGroupMember extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharedGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SharedGroup sharedGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Builder
    public SharedGroupMember(SharedGroup sharedGroup, Member member) {
        this.sharedGroup = sharedGroup;
        this.member = member;
    }
}
