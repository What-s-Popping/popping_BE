package com.popping.data.member.service;

import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.data.member.repository.MemberRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<Member> findMember(Long memberPk) {
        return memberRepository.findById(memberPk);
    }

    public List<Member> findMembers(List<Long> memberPks) {
        return memberRepository.findAllById(memberPks);
    }

    public Member findMemberByToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage()));
    }

    public Optional<Member> findMemberByPhoneNum(String phoneNum) {
        return memberRepository.findByPhoneNumber(phoneNum);
    }

    public int findPopcornBalance(Long memberPk) {
        return memberRepository.findPopcorn(memberPk).orElseThrow(NoSuchElementException::new);
    }
}
