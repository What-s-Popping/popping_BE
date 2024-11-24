package com.popping.data.popcorn.repository;

import com.popping.data.popcorn.entity.PopcornPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopcornRepository extends JpaRepository<PopcornPayment, Long> {
}
