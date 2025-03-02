package com.popping.data.block.repository;

import com.popping.data.block.entity.BlockMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockMemberRepository extends JpaRepository<BlockMember, Long> {
    @Query("select bm.toMember.pk from BlockMember bm where bm.fromMember.pk = :requesterPk")
    List<Long> findBlockMemberPks(@Param("requesterPk") Long requesterPk);
    boolean existsByFromMember_PkAndToMember_Pk(Long fromMemberPk, Long toMemberPk);

    @Modifying
    @Query("delete from BlockMember bm " +
            "where bm.toMember.pk = :toMemberPk and bm.fromMember.pk = :fromMemberPk")
    void deleteBlockMember(@Param("toMemberPk") Long toMemberPk, @Param("fromMemberPk") Long fromMemberPk);
}
