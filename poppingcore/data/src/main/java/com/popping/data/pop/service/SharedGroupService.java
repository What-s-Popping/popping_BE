package com.popping.data.pop.service;

import com.popping.data.pop.entity.SharedGroup;
import com.popping.data.pop.repository.SharedGroupRepository;
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
