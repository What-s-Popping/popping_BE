package com.popping.domain.frame.service;

import com.popping.data.frame.entity.Frame;
import com.popping.data.frame.service.FrameService;
import com.popping.domain.frame.dto.FrameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindFrameService {
    private final FrameService frameService;

    public FrameDto.Response findDisplayedFrame() {
        Frame frame = frameService.findDisplayedFrame();

        return FrameDto.Response.builder()
                .key(frame.getName())
                .title(frame.getTitle())
                .build();
    }
}
