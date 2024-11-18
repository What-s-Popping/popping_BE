package com.popping.data.post.service;

import com.popping.data.post.entity.Pop;
import com.popping.data.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PopService {
    private final PostRepository postRepository;

    public void save(Pop pop) {
        postRepository.save(pop);
    }

    public LocalDateTime findLastPrivateProfilePopDate(Long memberPk) {
        return postRepository.findLastPrivateProfilePostDate(memberPk);
    }

    public int findTotalCntPrivateProfilePop(Long memberPk) {
        return postRepository.findTotalCntPrivateProfilePop(memberPk);
    }
}
