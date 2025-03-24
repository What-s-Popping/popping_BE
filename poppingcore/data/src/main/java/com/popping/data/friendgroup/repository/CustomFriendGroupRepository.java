package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.CustomFriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomFriendGroupRepository extends JpaRepository<CustomFriendGroup, Long> {
    @Transactional
    @Modifying
    @Query("update CustomFriendGroup c set c.groupName = :customGroupName where c.pk = :groupId and c.groupOwner.pk = :requesterPk")
    void updateCustomFriendGroupName(@Param("groupId") long groupId,@Param("customGroupName") String customGroupName, @Param("requesterPk")Long requesterPk);

    @Query("select c from CustomFriendGroup c where c.pk = :groupId and c.groupOwner.pk = :groupOwnerPk")
    Optional<CustomFriendGroup> findCustomFriendGroup(@Param("groupId") long groupId, @Param("groupOwnerPk") long groupOwnerPk);

    @Query("select c from CustomFriendGroup c where c.groupOwner.pk = :groupOwnerPk")
    List<CustomFriendGroup> findCustomFriendGroups(@Param("groupOwnerPk") long groupOwnerPk);
}
