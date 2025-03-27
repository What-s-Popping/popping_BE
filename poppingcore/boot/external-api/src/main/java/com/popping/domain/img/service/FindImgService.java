package com.popping.domain.img.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FindImgService {
    private final S3Service s3Service;

    public String generatePopImgDownloadUrl(String imgName) {
        return generateDownloadUrl(S3ImgPathPrefix.POP, imgName);
    }

    public String generateRePopImgDownloadUrl(String imgName) {
        return generateDownloadUrl(S3ImgPathPrefix.RE_POP, imgName);
    }

    public String generateProfileImgDownloadUrl(String imgName) {
        return generateDownloadUrl(S3ImgPathPrefix.PROFILE, imgName);
    }

    public String generateProfileImgDownloadUrl(String imgName, boolean isPrivateProfile) {
        return isPrivateProfile ? null : generateDownloadUrl(S3ImgPathPrefix.PROFILE, imgName);
    }

    private String generateDownloadUrl(S3ImgPathPrefix pathPrefix, String imgName) {
        if (Objects.isNull(imgName)) {
            return null;
        }

        return s3Service.generateGetPresignedUrl(pathPrefix, imgName);
    }

    public boolean isNotProfileImgSaved(String imgName) {
        return !s3Service.isImgSaved(S3ImgPathPrefix.PROFILE, imgName);
    }

    public boolean isNotPopImgSaved(String imgName) {
        return !s3Service.isImgSaved(S3ImgPathPrefix.POP, imgName);
    }

    public boolean isNotRePopImgSaved(String imgName) {
        return !s3Service.isImgSaved(S3ImgPathPrefix.RE_POP, imgName);
    }
}
