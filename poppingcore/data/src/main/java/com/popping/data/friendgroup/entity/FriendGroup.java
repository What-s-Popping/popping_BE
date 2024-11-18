package com.popping.data.friendgroup.entity;

import com.popping.data.member.data.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendGroup extends CommonGroup {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupOwner", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member groupOwner;

    @Builder
    public FriendGroup(Member groupOwner) {
        this.groupOwner = groupOwner;
    }
}
