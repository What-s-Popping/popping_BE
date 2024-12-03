package com.popping.data.pop.repository;

import com.popping.data.pop.entity.Pop;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PopRepository extends JpaRepository<Pop,Long> {
    @Query("select p.createdAt from Pop p where p.writer.pk = :memberPk and p.isPrivateProfile = true order by p.pk desc limit 1")
    LocalDateTime findLastPrivateProfilePostDate(@Param("memberPk") Long memberPk);

    @Query("select count(p) from Pop p where p.isPrivateProfile = true and p.writer.pk = :memberPk")
    int findTotalCntPrivateProfilePop(@Param("memberPk") Long memberPk);

    @Query("select p from Pop p where (:lastId is null or p.pk < :lastId) and p.writer.pk = :memberPk order by p.pk desc ")
    List<Pop> findMyPopNextPage(@Param("lastId") Long lastId, @Param("memberPk") Long memberPk, PageRequest pageRequest);

    @Query("select p from Pop p " +
                "join fetch p.writer " +
                "left join SharedGroupMember sgm on p.sharedGroup = sgm.sharedGroup " +
                "left join FriendGroupMember fgm on p.sharedGroup = fgm.friendGroup " +
            "where (sgm.member.pk = :requesterPk or fgm.member.pk = :requesterPk) " +
                "and (:lastPk is null or p.pk < :lastPk) " +
                "and p.createdAt > (current_timestamp - 1 day) " +
                "and p.writer.pk not in :blockMemberPks " +
                "and p.pk not in :reportPopPks " +
            "order by p.pk desc")
    List<Pop> findFriendPops(@Param("lastPk") Long lastPk,
                             @Param("requesterPk") Long requesterPk,
                             @Param("reportPopPks") List<Long> reportPopPks,
                             @Param("blockMemberPks") List<Long> blockMemberPks,
                             Pageable pageable);
}
