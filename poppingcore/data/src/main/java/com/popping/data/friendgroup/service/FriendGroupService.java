package com.popping.data.friendgroup.service;

import com.popping.data.friendgroup.entity.FriendGroup;
import com.popping.data.friendgroup.repository.FriendGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FriendGroupService {
    private final FriendGroupRepository friendGroupRepository;

    public void saveFriendGroup(FriendGroup friendGroup) {
        friendGroupRepository.save(friendGroup);
    }
    public FriendGroup findFriendGroup(Long ownerPk) {
        return friendGroupRepository.findFriendGroup(ownerPk).orElseThrow(NoSuchElementException::new);
    }
}
