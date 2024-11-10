package com.popping.domain.img.service;

import com.popping.client.aws.s3.S3Service;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ImgSaveService {
    private final S3Service s3Service;

    private static final String PROFILE_PATH = "profile/";
    private static final String DEFAULT_EXTENSION = "jpeg";

    public void saveProfileImg(String imgName, String extension, MultipartFile img) {
        if (img.isEmpty()) {
            throw new NoSuchElementException(ExceptionMessage.IMG_NOT_EXIST.getMessage());
        }
        if (!extension.equalsIgnoreCase(DEFAULT_EXTENSION)) {
            throw new UnsupportedOperationException(ExceptionMessage.UNSUPPORTED_IMAGE_FORMAT.getMessage());
        }

        try {
            String imgOriginalName = imgName + "." + extension;
            s3Service.saveImg(PROFILE_PATH, imgOriginalName, img.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
