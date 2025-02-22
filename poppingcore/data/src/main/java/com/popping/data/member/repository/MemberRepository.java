package com.popping.data.member.repository;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.member.entity.Member;
import com.popping.data.member.entity.signupplatform.SignUpPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.email = :email and m.signUpPlatform = :signUpPlatform")
    Optional<Member> findMember(@Param("email") String email, @Param("signUpPlatform") SignUpPlatform signUpPlatform);
    @Query("select m from Member m where m.refreshToken = :refreshToken")
    Optional<Member> findByRefreshToken(@Param("refreshToken") String refreshToken);
    @Query("select m from Member m where m.phoneNumber = :phoneNumber")
    Optional<Member> findByPhoneNumber(@Param("phoneNumber") String phoneNum);
    @Query("select m.popCorn from Member m where m.pk = :memberPk")
    Optional<Integer> findPopcorn(@Param("memberPk") Long memberPk);
    boolean existsByEmailAndSignUpPlatform(String email, SignUpPlatform signUpPlatform);

    @Transactional
    @Modifying
    @Query("update Member m set m.allFriendGroup = :friendGroup where m.pk = :memberPk")
    void updateAllFriendGroup(@Param("friendGroup") FriendGroup friendGroup, @Param("memberPk") Long memberPk);

    @Query("select m from Member m where m.phoneNumber in :phoneNumbers")
    List<Member> findMembers(@Param("phoneNumbers") List<String> phoneNumbers);
}
