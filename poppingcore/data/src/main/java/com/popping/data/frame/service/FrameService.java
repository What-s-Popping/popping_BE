package com.popping.data.frame.service;

import com.popping.data.frame.entity.Frame;
import com.popping.data.frame.repository.FrameRepository;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrameService {
    private final FrameRepository frameRepository;

    public Frame findDisplayedFrame() {
        return frameRepository.findByIsDisplayed()
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.DISPLAYED_FRAME_NOT_FOUND.getMessage()));
    }
}
