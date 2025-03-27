package com.popping.data.share.repository;

import com.popping.data.share.entity.Shared;
import com.popping.data.share.entity.SharedPlatform;
import com.popping.data.share.entity.SharedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SharedRepository extends JpaRepository<Shared, Long> {

    @Query("select s from Shared s " +
            "where s.sharedPlatform = :sharedPlatform " +
            "and s.sharedType = :sharedType " +
            "and s.sharedMember.pk = :requesterPk")
    Optional<Shared> findShared(@Param("sharedPlatform") SharedPlatform sharedPlatform,
                                @Param("sharedType") SharedType sharedType,
                                @Param("requesterPk") Long requesterPk);

    @Modifying
    @Transactional
    @Query("delete from Shared s where s.sharedMember.pk = :memberPk")
    void deleteAllShared(@Param("memberPk") Long memberPk);
}
