package com.popping.data.post.service;

import com.popping.data.post.entity.SharedGroup;
import com.popping.data.post.repository.SharedGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedGroupService {
    private final SharedGroupRepository sharedGroupRepository;

    public void saveSharedGroup(SharedGroup sharedGroup) {
        sharedGroupRepository.save(sharedGroup);
    }
}
