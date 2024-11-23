package com.popping.data.post.repository;

import com.popping.data.post.entity.Pop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Pop,Long> {
    @Query("select p.createdAt from Pop p where p.writer.pk = :memberPk and p.isPrivateProfile = true order by p.pk desc limit 1")
    LocalDateTime findLastPrivateProfilePostDate(@Param("memberPk") Long memberPk);

    @Query("select count(p) from Pop p where p.isPrivateProfile = true and p.writer.pk = :memberPk")
    int findTotalCntPrivateProfilePop(@Param("memberPk") Long memberPk);
}

