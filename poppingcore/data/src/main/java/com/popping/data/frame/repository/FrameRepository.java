package com.popping.data.frame.repository;

import com.popping.data.frame.entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FrameRepository extends JpaRepository<Frame, Long> {
    @Query("select f from Frame f where f.isDisplayed = true")
    Optional<Frame> findByIsDisplayed();
}
