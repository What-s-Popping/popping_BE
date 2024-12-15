package com.popping.data.pop.service;

import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.PopRead;
import com.popping.data.pop.repository.PopReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopReadService {
    private final PopReadRepository popReadRepository;

    public List<PopRead> findPopReads(List<Pop> pops, Long readerPk) {
        return  popReadRepository.findPopRead(pops, readerPk);
    }
}
