package com.popping.data.pop.repository;

import com.popping.data.pop.entity.RePopActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RePopActionStateRepository extends JpaRepository<RePopActionState, Long> {
    @Query("select rpas from RePopActionState rpas " +
            "where rpas.member.pk = :memberPk " +
                "and rpas.rePop.pk = :popPk " +
                "and rpas.actionState = 'IMG_SAVED'")
    Optional<RePopActionState> findImgSavedState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select rpas from RePopActionState rpas " +
            "where rpas.member.pk = :memberPk " +
                "and rpas.rePop.pk = :popPk " +
                "and rpas.actionState = 'SHARED'")
    Optional<RePopActionState> findSharedState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select rpas from RePopActionState rpas " +
            "where rpas.member.pk = :memberPk " +
                "and rpas.rePop.pk = :popPk " +
                "and rpas.actionState = 'RE_POP'")
    Optional<RePopActionState> findRePopState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select rpas from RePopActionState rpas " +
            "where rpas.member.pk = :memberPk " +
                "and rpas.rePop.pk = :popPk " +
                "and rpas.actionState != 'RE_POP' " +
                "and rpas.actionState != 'SHARED' " +
                "and rpas.actionState != 'IMG_SAVED'")
    Optional<RePopActionState> findEmotionState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select r from RePopActionState r " +
            "join fetch r.member " +
            "where r.rePop.pk = :rePopPk")
    List<RePopActionState> findActions(@Param("rePopPk") Long rePopPk);

    @Modifying
    @Transactional
    @Query("delete from RePopActionState rpas where rpas.member.pk = :memberPk or rpas.rePop.writer.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}
