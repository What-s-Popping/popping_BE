package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendGroupMemberRepository extends JpaRepository<FriendGroupMember, String> {
    @Query("select f from FriendGroupMember f join fetch f.member where f.friendGroup = :friendGroup")
    List<FriendGroupMember> findFriendGroupMembersFetchMember(@Param("friendGroup") FriendGroup friendGroup);

    boolean existsByFriendGroup (FriendGroup friendGroup);

    @Query("select f from FriendGroupMember f " +
            "where f.friendGroup.groupOwner.pk = :groupOwnerPk " +
                "and f.member.pk = :groupMemberPk")
    List<FriendGroupMember> findFriendGroupMember(@Param("groupOwnerPk") Long groupOwnerPk, @Param("groupMemberPk") Long groupMemberPk);

    @Query("select f.member from FriendGroupMember f inner join f.member where f.friendGroup = :friendGroup")
    List<Member> findFriendGroupMembers(@Param("friendGroup") FriendGroup friendGroup);

    @Transactional
    @Modifying
    @Query("delete from FriendGroupMember f where f.friendGroup = :friendGroup and f.member.pk = :requesterPk")
    void deleteFriendGroupMember(FriendGroup friendGroup, long requesterPk);
}
