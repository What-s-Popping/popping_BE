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
}
