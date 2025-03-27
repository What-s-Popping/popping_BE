package com.popping.data.img.service;

import com.popping.data.img.entity.Img;
import com.popping.data.img.repository.ImgRepository;
import com.popping.data.pop.entity.RePop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void deleteAllAssociatedMember(Long memberPk) {
        imgRepository.deleteAllAssociatedMember(memberPk);
    }
}
