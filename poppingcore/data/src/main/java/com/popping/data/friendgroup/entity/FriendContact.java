package com.popping.data.friendgroup.entity;

import com.popping.data.member.entity.Member;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendContact extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member friend;

    @Builder
    public FriendContact(Member friend, Member owner) {
        this.friend = friend;
        this.owner = owner;
    }
}
