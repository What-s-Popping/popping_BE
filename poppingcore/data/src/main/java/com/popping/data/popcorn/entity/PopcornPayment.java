package com.popping.data.popcorn.entity;

import com.popping.data.member.entity.Member;
import com.popping.data.popcorn.entity.popcorntype.PopcornType;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopcornPayment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser")
    private Member purchaser;

    @NotNull
    private String transactionId;

    @NotNull
    private PopcornType popcornType;

    public PopcornPayment(Member purchaser, String transactionId, PopcornType popcornType) {
        this.purchaser = purchaser;
        this.transactionId = transactionId;
        this.popcornType = popcornType;
    }
}
