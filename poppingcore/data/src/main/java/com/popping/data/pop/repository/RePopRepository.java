package com.popping.data.pop.repository;

import com.popping.data.pop.entity.RePop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RePopRepository extends JpaRepository<RePop,Long> {
    // todo 신고한 rePop, 차단한 친구 필터링 로직 추가
    @Query("select rp from RePop rp " +
            "join fetch rp.writer " +
            "where (:lastPk is null or rp.pk < :lastPk) " +
                "and rp.targetMember.pk = :requesterPk " +
                "and rp.createdAt > (current_timestamp - 1 day) " +
            "order by rp.pk desc")
    List<RePop> findFriendRePops(@Param("lastPk") Long lastPk,
                                 @Param("requesterPk") Long requesterPk,
                                 Pageable pageable);
}

