package com.popping.data.member.entity;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.member.entity.ostype.OsType;
import com.popping.data.member.entity.role.Role;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @NotNull
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Column(name = "EMAIL", unique = true)
    private String email;
    @NotNull
    @Column(name = "PHONE_NUMBER", unique = true)
    private String phoneNumber;
    @NotNull
    @Column(name = "BIRTH_DATE")
    private String birthDate;
    @Column(name = "PRFOFILE_IMG_NAME")
    private String profileImgName;
    @Column(name = "FIREBASE_TOKEN", columnDefinition = "VARBINARY(400) NOT NULL")
    private String firebaseToken;
    @Column(name = "REFRESH_TOKEN", columnDefinition = "VARBINARY(400)")
    private String refreshToken;
    @Column(name = "IS_ALLOW_NOTIFY")
    private boolean isAllowNotify;

    @Enumerated(value = EnumType.STRING)
    private SignUpPlatform signUpPlatform;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private OsType osType;

    @Column
    private int popCorn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allFriendGroup",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private FriendGroup allFriendGroup;

    @Builder
    public Member(String birthDate, String email, String firebaseToken, boolean isAllowNotify, String name, String phoneNumber, String profileImgName, String refreshToken, SignUpPlatform signUpPlatform, OsType osType, FriendGroup allFriendGroup) {
        this.birthDate = birthDate;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.isAllowNotify = isAllowNotify;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImgName = profileImgName;
        this.refreshToken = refreshToken;
        this.role = Role.USER;
        this.signUpPlatform = signUpPlatform;
        this.osType = osType;
        this.allFriendGroup = allFriendGroup;
    }

    public void updateProfileImg(String profileImgName) {
        this.profileImgName = profileImgName;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
