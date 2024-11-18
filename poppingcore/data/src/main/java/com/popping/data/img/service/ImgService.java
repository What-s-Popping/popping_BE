package com.popping.data.img.service;

import com.popping.data.img.entity.Img;
import com.popping.data.img.repository.ImgRepository;
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
}
