package com.popping.data.friendgroup.entity;

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
public class CustomFriendGroupMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customFriendGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CustomFriendGroup customFriendGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Builder
    public CustomFriendGroupMember(CustomFriendGroup customFriendGroup, Member member) {
        this.customFriendGroup = customFriendGroup;
        this.member = member;
    }
}
