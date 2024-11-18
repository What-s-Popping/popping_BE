package com.popping.data.post.repository;

import com.popping.data.post.entity.SharedGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedGroupMemberRepository extends JpaRepository<SharedGroupMember, Long> {
}
