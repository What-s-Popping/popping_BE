package com.popping.domain.member.service;

import com.popping.client.aws.s3.S3ImgPathPrefix;
import com.popping.client.aws.s3.S3Service;
import com.popping.data.post.service.PopService;
import com.popping.domain.member.dto.PopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPopService {
    private final PopService popService;
    private final S3Service s3Service;

    public List<PopDto.Response> findMyPopNextPage(Optional<Long> lastIdx, Long memberPk) {
        return popService.findMyPopNextPage(lastIdx, memberPk)
                .stream().map(pop -> PopDto.Response.builder()
                        .createAt(pop.getCreatedAt())
                        .content(pop.getContents())
                        .imgUrl(pop.getColorChip()==null ?
                                s3Service.generateGetPresignedUrl(S3ImgPathPrefix.POP,pop.getImgName()) :
                                null)
                        .colorChip(pop.getColorChip())
                        .build())
                .toList();
    }
}
