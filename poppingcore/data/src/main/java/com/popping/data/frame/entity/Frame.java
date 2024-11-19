package com.popping.data.frame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Frame {
    @Id
    private Long pk;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "isDisplayed")
    private boolean isDisplayed;
}
