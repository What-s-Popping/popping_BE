package com.popping.data.member.data.member.repository;

import com.popping.data.member.data.member.entity.Member;
import com.popping.data.member.data.member.entity.signupplatform.SignUpPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.email = :email and m.signUpPlatform = :signUpPlatform")
    Optional<Member> findMember(@Param("email") String email, @Param("signUpPlatform") SignUpPlatform signUpPlatform);
    @Query("select m from Member m where m.refreshToken = :refreshToken")
    Optional<Member> findByRefreshToken(@Param("refreshToken") String refreshToken);
    @Query("select m from Member m where m.phoneNumber = :phoneNumber")
    Optional<Member> findByPhoneNumber(@Param("phoneNumber") String phoneNum);
}
