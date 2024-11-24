package com.popping.data.post.repository;

import com.popping.data.post.entity.Pop;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PopRepository extends JpaRepository<Pop,Long> {
    @Query("select count(p) from Pop p where p.isPrivateProfile = true and p.writer.pk = :memberPk")
    int findTotalCntPrivateProfilePop(@Param("memberPk") Long memberPk);

    @Query(value = "select p.createdAt " +
            "from Pop p " +
            "where p.writer.pk = :memberPk " +
                "and p.isPrivateProfile = true " +
                "and p.createdAt > :startDate " +
                "and p.createdAt < :endDate " +
            "order by p.pk desc limit 1")
    LocalDateTime findTodayLastPrivateProfilePopDate(@Param("memberPk") Long memberPk,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select p from Pop p where (:lastId is null or p.pk < :lastId) and p.writer.pk = :memberPk order by p.pk desc ")
    List<Pop> findMyPopNextPage(@Param("lastId") Long lastId, @Param("memberPk") Long memberPk, PageRequest pageRequest);
}

