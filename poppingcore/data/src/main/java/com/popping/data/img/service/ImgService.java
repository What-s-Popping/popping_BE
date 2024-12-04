package com.popping.data.img.service;

import com.popping.data.img.entity.Img;
import com.popping.data.img.repository.ImgRepository;
import com.popping.data.pop.entity.RePop;
import com.popping.global.exceptionmessage.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepository;

    public void saveImgNameWithoutPost(String imgName) {
        imgRepository.save(Img.builder()
                .imgName(imgName)
                .build()
        );
    }

    public void updateRePop(RePop rePop, String imgName) {
        imgRepository.updateRePop(rePop, imgName);
    }
}
