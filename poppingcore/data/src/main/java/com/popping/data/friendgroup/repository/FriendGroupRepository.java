package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendGroupRepository extends JpaRepository<FriendGroup, String> {
    @Query("select f from FriendGroup f where f.groupOwner.pk = :ownerPk")
    Optional<FriendGroup> findFriendGroup(@Param("ownerPk") Long ownerPk);
}
