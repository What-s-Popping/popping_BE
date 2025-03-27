package com.popping.data.friendreqeust.repository;

import com.popping.data.friendreqeust.entity.FriendRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("select f " +
            "from FriendRequest f " +
            "join fetch f.fromMember " +
            "where (:lastId is null or f.pk < :lastId) " +
                "and f.toMember.pk = :toMemberPk " +
            "order by f.pk desc")
    List<FriendRequest> findFriendsRequests(@Param("toMemberPk") Long toMemberPk, @Param("lastId") Long lastId, PageRequest pageRequest);

    @Query("select f " +
            "from FriendRequest f " +
            "where f.toMember.pk = :toMemberPk and f.fromMember.pk = :fromMemberPk")
    Optional<FriendRequest> findFriendsRequest(@Param("toMemberPk") Long toMemberPk, @Param("fromMemberPk") Long fromMemberPk);

    @Modifying
    @Transactional
    @Query("delete from FriendRequest f where f.toMember.pk = :memberPk or f.fromMember.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}
