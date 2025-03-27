package com.popping.data.pop.repository;

import com.popping.data.pop.entity.RePop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RePopRepository extends JpaRepository<RePop,Long> {
    @Query("select rp from RePop rp " +
            "join fetch rp.writer " +
            "where (:lastPk is null or rp.pk < :lastPk) " +
                "and rp.targetMember.pk = :requesterPk " +
//                "and rp.createdAt > (current_timestamp - 1 day) " + todo 실제 배포때는 주석 해제
                "and rp.writer.pk not in :blockMemberPks " +
                "and rp.pk not in :reportRePopPks " +
            "order by rp.pk desc")
    List<RePop> findNotExpiredFriendRePops(@Param("lastPk") Long lastPk,
                                           @Param("requesterPk") Long requesterPk,
                                           @Param("reportRePopPks") List<Long> reportRePopPks,
                                           @Param("blockMemberPks") List<Long> blockMemberPks,
                                           Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from RePop rp where rp.writer.pk = :memberPk or rp.targetMember.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}

