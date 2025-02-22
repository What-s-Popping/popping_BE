package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.entity.FriendGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendGroupMemberRepository extends JpaRepository<FriendGroupMember, String> {
    @Query("select f from FriendGroupMember f join fetch f.member where f.friendGroup = :friendGroup")
    List<FriendGroupMember> findFriendGroupMembersFetchMember(@Param("friendGroup") FriendGroup friendGroup);

    boolean existsByFriendGroup (FriendGroup friendGroup);

    @Query("select f from FriendGroupMember f " +
            "where f.friendGroup.groupOwner.pk = :groupOwnerPk " +
                "and f.member.pk = :groupMemberPk")
    List<FriendGroupMember> findFriendGroupMember(@Param("groupOwnerPk") Long groupOwnerPk, @Param("groupMemberPk") Long groupMemberPk);

    @Query("select f.member.firebaseToken from FriendGroupMember f inner join f.member where f.friendGroup = :friendGroup")
    List<String> findFriendGroupMemberFCMTokens(@Param("friendGroup") FriendGroup friendGroup);
}
