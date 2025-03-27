package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.FriendContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendContactRepository extends JpaRepository<FriendContact, Long> {
    @Query(value = """
            select fc.friend from friend_contact fc
            where fc.owner = :ownerPk
            order by RAND() limit 10
           """, nativeQuery = true)
    List<Long> findFriends(@Param("ownerPk") Long ownerPk);

    @Modifying
    @Transactional
    @Query("delete from FriendContact fc where fc.owner.pk = :memberPk or fc.friend.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}
