package com.popping.data.pop.repository;

import com.popping.data.pop.entity.PopActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
