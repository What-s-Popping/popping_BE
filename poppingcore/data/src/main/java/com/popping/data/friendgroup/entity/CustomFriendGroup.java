package com.popping.data.friendgroup.entity;

import com.popping.data.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomFriendGroup extends CommonGroup {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupOwner", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member groupOwner;

    @Column
    private String groupName;

    @Column
    private String groupImgName;


    @Builder
    public CustomFriendGroup(Member groupOwner, String groupName, String groupImgName) {
        this.groupOwner = groupOwner;
        this.groupName = groupName;
        this.groupImgName = groupImgName;
    }
}
