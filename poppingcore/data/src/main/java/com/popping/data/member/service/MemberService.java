package com.popping.data.member.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import com.popping.data.member.repository.MemberRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<Member> findMemberOp(Long memberPk) {
        return memberRepository.findById(memberPk);
    }

    public Member findMember(Long memberPk) {
        return memberRepository.findById(memberPk)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage()));
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

    public boolean isSignUpMember(String email, SignUpPlatform signUpPlatform) {
        return memberRepository.existsByEmailAndSignUpPlatform(email, signUpPlatform);
    }

    @Transactional
    public void updateFriendGroup(Long memberPk, FriendGroup friendGroup) {
        memberRepository.updateAllFriendGroup(friendGroup, memberPk);
    }

    public List<Member> findMembersByPhoneNumber(List<String> phoneNumbers) {
        return memberRepository.findMembers(phoneNumbers);
    }

    public String findMemberName(Long memberPk) {
        return memberRepository.findMemberName(memberPk)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.MEMBER_NOT_FOUND.getMessage()));
    }
}
