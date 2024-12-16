package com.popping.domain.img.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import com.popping.data.img.service.ImgService;
import com.popping.domain.img.dto.ImgDto;
import com.popping.global.exceptionmessage.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveImgService {
    private final S3Service s3Service;
    private final ImgService imgService;

    private static final String PROFILE_PATH = "profile/";
    private static final String POST_PATH = "post/";
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

    public ImgDto.Response generatePopUploadUrl() {
        String randomImgName = generateImgName();
        String uploadUrl = s3Service.generatePutPresignedUrl(S3ImgPathPrefix.POP, randomImgName);
        imgService.saveImgNameWithoutPost(randomImgName);

        return ImgDto.Response.builder()
                .imgName(randomImgName)
                .presignedUrl(uploadUrl)
                .build();
    }

    public ImgDto.Response generateRePopUploadUrl() {
        String randomImgName = generateImgName();
        String uploadUrl = s3Service.generatePutPresignedUrl(S3ImgPathPrefix.RE_POP, randomImgName);
        imgService.saveImgNameWithoutPost(randomImgName);

        return ImgDto.Response.builder()
                .imgName(randomImgName)
                .presignedUrl(uploadUrl)
                .build();
    }

    private static String generateImgName() {
        return UUID.randomUUID().toString().concat("." + DEFAULT_EXTENSION);
    }
}
