package com.popping.data.pop.repository;

import com.popping.data.pop.entity.SharedGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SharedGroupMemberRepository extends JpaRepository<SharedGroupMember, Long> {
    @Modifying
    @Transactional
    @Query("delete from SharedGroupMember s where s.member.pk = :memberPk or s.sharedGroup.pk in :sharedGroupPks")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk, @Param("sharedGroupPks") List<Long> sharedGroupPks);

    @Transactional
    @Modifying
    @Query("delete from SharedGroupMember s where s.sharedGroup in :memberAllSharedGroupPk and s.member.pk = :requesterPk")
    void deleteSharedGroupMember(@Param("memberAllSharedGroupPk") List<Long> memberAllSharedGroupPk, @Param("requesterPk") long requesterPk);
}
