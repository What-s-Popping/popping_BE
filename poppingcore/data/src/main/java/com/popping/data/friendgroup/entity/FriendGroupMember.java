package com.popping.data.friendgroup.entity;

import com.popping.data.member.data.member.entity.Member;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendGroupMember extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private FriendGroup friendGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Builder
    public FriendGroupMember(FriendGroup friendGroup, Member member) {
        this.friendGroup = friendGroup;
        this.member = member;
    }
}
