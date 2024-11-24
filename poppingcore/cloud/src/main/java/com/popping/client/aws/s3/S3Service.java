package com.popping.client.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private static final Duration EXPIRY_TIME = Duration.ofHours(24L);

    public void saveImg(String imgPath, String imgOriginalName, byte[] file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(imgPath + imgOriginalName)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    public String generatePutPresignedUrl(S3ImgPathPrefix imgPathPrefix, String imgName) {

        return s3Presigner.presignPutObject(PutObjectPresignRequest.builder()
                        .putObjectRequest(PutObjectRequest.builder()
                                .bucket(bucket)
                                .key(imgPathPrefix.getPathPrefix() + imgName)
                                .build())
                        .signatureDuration(EXPIRY_TIME)
                        .build())
                .url().toString();
    }

    public String generateGetPresignedUrl(S3ImgPathPrefix imgPathPrefix, String imgName) {
        return s3Presigner.presignGetObject(
                        GetObjectPresignRequest.builder()
                                .getObjectRequest(GetObjectRequest.builder()
                                        .bucket(bucket)
                                        .key(imgPathPrefix.getPathPrefix() + imgName)
                                        .build())
                                .signatureDuration(EXPIRY_TIME)
                                .build())
                .url()
                .toString();
    }

    public boolean isImgSaved(String filePath, String imgName) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath + imgName)
                    .build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
