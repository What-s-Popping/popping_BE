package com.popping.data.img.repository;

import com.popping.data.img.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<Img,Long> {
}
