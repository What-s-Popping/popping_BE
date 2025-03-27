package com.popping.data.pop.repository;

import com.popping.data.pop.entity.PopActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PopActionStateRepository extends JpaRepository<PopActionState, Long> {
    @Query("select pas from PopActionState pas " +
            "where pas.member.pk = :memberPk " +
                "and pas.pop.pk = :popPk " +
                "and pas.actionState = 'IMG_SAVED'")
    Optional<PopActionState> findImgSavedState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select pas from PopActionState pas " +
            "where pas.member.pk = :memberPk " +
                "and pas.pop.pk = :popPk " +
                "and pas.actionState = 'SHARED'")
    Optional<PopActionState> findSharedState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select pas from PopActionState pas " +
            "where pas.member.pk = :memberPk " +
                "and pas.pop.pk = :popPk " +
                "and pas.actionState = 'RE_POP'")
    Optional<PopActionState> findRePopState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select pas from PopActionState pas " +
            "where pas.member.pk = :memberPk " +
                "and pas.pop.pk = :popPk " +
                "and pas.actionState != 'RE_POP' " +
                "and pas.actionState != 'SHARED' " +
                "and pas.actionState != 'IMG_SAVED'")
    Optional<PopActionState> findEmotionState(@Param("memberPk") Long memberPk, @Param("popPk") Long popPk);

    @Query("select p from PopActionState p " +
            "join fetch p.member " +
            "where p.pop.pk = :popPk")
    List<PopActionState> findActions(@Param("popPk") Long popPk);

    @Modifying
    @Transactional
    @Query("delete from PopActionState pas where pas.member.pk = :memberPk or pas.pop.writer.pk = :memberPk")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);

    @Transactional
    @Modifying
    @Query("delete from PopActionState p where p.pop.writer.pk = :popWriter and p.member.pk = :actionWriter")
    void deletePopActionState(@Param("popWriter") long popWriter, @Param("actionWriter") long actionWriter);
}
