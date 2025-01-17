package com.popping.data.pop.repository;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.PopRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopReadRepository extends JpaRepository<PopRead, Long> {
    @Query("select pr.pop.pk from PopRead pr " +
            "where pr.pop in :pops " +
                "and pr.reader.pk = :readerPk")
    List<Long> findReadPopPks(@Param("pops") List<Pop> pops, @Param("readerPk") Long readerPk);
    boolean existsByPopAndReader(Pop pop, Member reader);
}
