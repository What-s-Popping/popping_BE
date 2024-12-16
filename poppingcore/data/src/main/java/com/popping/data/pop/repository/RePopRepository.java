package com.popping.data.pop.repository;

import com.popping.data.pop.entity.RePop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RePopRepository extends JpaRepository<RePop,Long> {
    @Query("select rp from RePop rp " +
            "join fetch rp.writer " +
            "where (:lastPk is null or rp.pk < :lastPk) " +
                "and rp.targetMember.pk = :requesterPk " +
                "and rp.createdAt > (current_timestamp - 1 day) " +
                "and rp.writer.pk not in :blockMemberPks " +
                "and rp.pk not in :reportRePopPks " +
            "order by rp.pk desc")
    List<RePop> findNotExpiredFriendRePops(@Param("lastPk") Long lastPk,
                                           @Param("requesterPk") Long requesterPk,
                                           @Param("reportRePopPks") List<Long> reportRePopPks,
                                           @Param("blockMemberPks") List<Long> blockMemberPks,
                                           Pageable pageable);
}

