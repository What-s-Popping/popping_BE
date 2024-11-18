package com.popping.data.post.service;

import com.popping.data.post.entity.SharedGroupMember;
import com.popping.data.post.repository.SharedGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedGroupMemberService {
    private final SharedGroupMemberRepository sharedGroupMemberRepository;
    public void saveSharedGroupMember(SharedGroupMember sharedGroupMember) {
        sharedGroupMemberRepository.save(sharedGroupMember);
    }

    public void saveSharedGroupMembers(List<SharedGroupMember> sharedGroupMembers) {
        sharedGroupMemberRepository.saveAll(sharedGroupMembers);
    }
}
