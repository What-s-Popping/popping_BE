package com.popping.data.img.repository;

import com.popping.data.img.entity.Img;
import com.popping.data.pop.entity.RePop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImgRepository extends JpaRepository<Img,Long> {
    @Modifying
    @Query("update Img i set i.rePop = :rePop where i.imgName = :imgName")
    void updateRePop(@Param("rePop") RePop rePop, @Param("imgName") String imgName);

    @Modifying
    @Transactional
    @Query("delete from Img i " +
                "where i.pop.pk in (select p.pk from Pop p where p.writer.pk = :memberPk)" +
                "or i.rePop.pk in (select rp.pk from RePop rp where rp.writer.pk = :memberPk)")
    void deleteAllAssociatedMember(@Param("memberPk") Long memberPk);
}
