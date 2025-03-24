package com.popping.data.pop.service;

import com.popping.data.member.entity.Member;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.PopRead;
import com.popping.data.pop.repository.PopReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopReadService {
    private final PopReadRepository popReadRepository;

    public List<Long> findReadPopPks(List<Pop> pops, Long readerPk) {
        return popReadRepository.findReadPopPks(pops, readerPk);
    }

    @Transactional
    public void readPop(Pop pop, Member reader) {
        if (popReadRepository.existsByPopAndReader(pop, reader)) {
            return;
        }

        popReadRepository.save(
                PopRead.builder()
                        .pop(pop)
                        .reader(reader)
                        .build());
    }

    public void deleteReadPopsHistory(long writerPk, long readerPk) {
        popReadRepository.deleteReadPopsHistory(writerPk, readerPk);
    }
}
