package com.popping.data.member.data.member.service;

import com.popping.data.member.data.member.entity.Member;
import com.popping.data.member.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.data.member.data.member.repository.MemberRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findMember(String email, SignUpPlatform signUpPlatform) {
        return memberRepository.findMember(email, signUpPlatform);
    }

    public Member findMemberByToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage()));
    }

    public Optional<Member> findMemberByPhoneNum(String phoneNum) {
        return memberRepository.findByPhoneNumber(phoneNum);
    }
}
