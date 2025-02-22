package com.popping.data.friendgroup.repository;

import com.popping.data.friendgroup.entity.FriendContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendContactRepository extends JpaRepository<FriendContact, Long> {
}
