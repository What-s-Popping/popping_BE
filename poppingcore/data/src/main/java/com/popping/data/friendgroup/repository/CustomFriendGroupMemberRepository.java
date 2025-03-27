package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.CustomFriendGroup;
import com.popping.data.friendgroup.entity.CustomFriendGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CustomFriendGroupMemberRepository extends JpaRepository<CustomFriendGroupMember, Long> {

    @Query("select c from CustomFriendGroupMember c where c.customFriendGroup = :customFriendGroup")
    List<CustomFriendGroupMember> findCustomFriendGroupMembers(@Param("customFriendGroup") CustomFriendGroup customFriendGroup);

    @Query("select c from CustomFriendGroupMember c where c.customFriendGroup = :customFriendGroup and c.member.pk = :memberId")
    Optional<CustomFriendGroupMember> findCustomFriendGroupMember(@Param("customFriendGroup") CustomFriendGroup customFriendGroup, @Param("memberId") long memberId);

    @Query("select c from CustomFriendGroupMember c join fetch c.member where c.customFriendGroup in :customFriendGroups")
    List<CustomFriendGroupMember> findCustomFriendGroupMembers(@Param("customFriendGroups")List<CustomFriendGroup> customFriendGroups);

    @Transactional
    @Modifying
    @Query("delete from CustomFriendGroupMember c where c.customFriendGroup in :memberCustomFriendGroup and c.member.pk = :requesterPk")
    void deleteCustomFriendGroupMember(@Param("memberCustomFriendGroup") List<CustomFriendGroup> memberCustomFriendGroup, @Param("requesterPk") long requesterPk);
}
