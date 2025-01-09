package com.popping.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.PolicyTerm;
import com.popping.data.member.entity.ostype.OsType;
import com.popping.data.member.entity.role.Role;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.domain.member.dto.MemberInfoDto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class AuthMemberDto {
    @Getter
    @Setter
    public static class SignUpRequest {
        @NotBlank
        private final String name;
        @NotBlank @Email
        private final String email;
        @NotBlank
        private final String phoneNumber;
        @NotBlank
        private final String birthDate;
        @NotBlank
        private final String firebaseToken;
        private final String extension;
        @JsonProperty(value = "isAllowNotify")
        private final boolean isAllowNotify;
        @JsonProperty(value = "isAllowTermOne")
        private final boolean isAllowTermOne;
        @JsonProperty(value = "isAllowTermTwo")
        private final boolean isAllowTermTwo;
        @JsonProperty(value = "isAllowTermThree")
        private final boolean isAllowTermThree;
        private final SignUpPlatform signUpPlatform;
        private final OsType osType;
        private final MultipartFile file;

        @Builder
        public SignUpRequest(String name, String birthDate, String email, String firebaseToken, String extension, boolean isAllowNotify, boolean isAllowTermOne, boolean isAllowTermThree, boolean isAllowTermTwo, String phoneNumber, SignUpPlatform signUpPlatform, OsType osType, MultipartFile file) {
            this.name = name;
            this.birthDate = birthDate;
            this.email = email;
            this.firebaseToken = firebaseToken;
            this.extension = extension;
            this.isAllowNotify = isAllowNotify;
            this.isAllowTermOne = isAllowTermOne;
            this.isAllowTermThree = isAllowTermThree;
            this.isAllowTermTwo = isAllowTermTwo;
            this.phoneNumber = phoneNumber;
            this.signUpPlatform = signUpPlatform;
            this.osType = osType;
            this.file = file;
        }

        public boolean isExistNotAllowPolicies() {
            return !(isAllowNotify && isAllowTermOne && isAllowTermTwo);
        }

        public Member of() {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .birthDate(birthDate)
                    .isAllowNotify(isAllowNotify)
                    .signUpPlatform(signUpPlatform)
                    .firebaseToken(firebaseToken)
                    .osType(osType)
                    .build();
        }

        public PolicyTerm of(Member member) {
            return PolicyTerm.builder()
                    .member(member)
                    .isAllowTermOne(isAllowTermOne)
                    .isAllowTermTwo(isAllowTermTwo)
                    .isAllowTermThree(isAllowTermThree)
                    .build();
        }
    }

    @Getter
    public static class SignUpResponse {
        private TokenDto.Tokens tokens;

        @Builder
        public SignUpResponse(TokenDto.Tokens token) {
            this.tokens = token;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SignInRequest {
        @NotBlank @Email
        private String email;
        private SignUpPlatform signUpPlatform;

        @Builder
        public SignInRequest(String email, SignUpPlatform signUpPlatform) {
            this.email = email;
            this.signUpPlatform = signUpPlatform;
        }
    }

    @Getter
    @Setter
    public static class SignInResponse {
        private TokenDto.Tokens tokens;
        @JsonProperty(value = "isRegistered")
        private boolean isRegistered;
        private MemberInfo memberInfo;

        @Builder
        public SignInResponse(boolean isRegistered, TokenDto.Tokens tokens, Member member) {
            this.isRegistered = isRegistered;
            this.tokens = tokens;
            this.memberInfo = isRegistered ? new MemberInfo(member) : null;
        }

        public static SignInResponse of(boolean isRegistered) {
            return SignInResponse.builder()
                    .isRegistered(isRegistered)
                    .tokens(TokenDto.Tokens.builder().build())
                    .build();
        }

        public static SignInResponse of(boolean isRegistered, TokenDto.Tokens tokens, Member member) {
            return SignInResponse.builder()
                    .isRegistered(isRegistered)
                    .tokens(tokens)
                    .member(member)
                    .build();
        }



        @Getter
        @Setter
        private static class MemberInfo {
            private String name;
            private String email;
            private String phoneNumber;
            private String birthDate;
            private String firebaseToken;
            @JsonProperty(value = "isAllowNotify")
            private boolean isAllowNotify;
            private SignUpPlatform signUpPlatform;
            private Role role;
            private OsType osType;

            private MemberInfo(Member member) {
                this.name = member.getName();
                this.email = member.getEmail();
                this.phoneNumber = member.getPhoneNumber();
                this.birthDate = member.getBirthDate();
                this.firebaseToken = member.getFirebaseToken();
                this.signUpPlatform = member.getSignUpPlatform();
                this.role = member.getRole();
                this.osType = member.getOsType();
            }
        }
    }

    @Getter
    @Setter
    public static class SignInRequiredResponse {
        private TokenDto.Token accessToken;
        @JsonProperty(value = "isSignInRequired")
        private boolean isSignInRequired;

        @Builder
        public SignInRequiredResponse(TokenDto.Token accessToken, boolean isSignInRequired) {
            this.isSignInRequired = isSignInRequired;
            this.accessToken = accessToken;
        }
    }
}
