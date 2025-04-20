package com.popping.domain.widget.service;

import com.popping.client.aws.s3.S3Service;
import com.popping.data.pop.entity.BasePop;
import com.popping.data.pop.entity.Pop;
import com.popping.data.pop.entity.RePop;
import com.popping.data.pop.service.PopService;
import com.popping.data.pop.service.RePopService;
import com.popping.domain.img.service.FindImgService;
import com.popping.domain.widget.dto.WidgetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WidgetService {
    private final RePopService rePopService;
    private final PopService popService;
    private final FindImgService findImgService;

    public Optional<WidgetDto.Response> findRecentWidget(Long memberPk) {
        Optional<Pop> widgetPopOp = popService.findRecentWidgetPop(memberPk);
        Optional<RePop> widgetRePopOp = rePopService.findRecentWidgetRePop(memberPk);

        if (widgetPopOp.isEmpty() && widgetRePopOp.isEmpty()) {
            return Optional.empty();
        }

        if (widgetPopOp.isPresent() && widgetRePopOp.isPresent()) {
            return widgetPopOp.get().getCreatedAt().isAfter(widgetRePopOp.get().getCreatedAt())
                    ? Optional.of(
                            WidgetDto.Response.createPopWidget(widgetPopOp.get(), findImgService.generatePopImgDownloadUrl(widgetPopOp.get().getImgName()))
                      )
                    : Optional.of(
                            WidgetDto.Response.createRePopWidget(widgetRePopOp.get(), findImgService.generateRePopImgDownloadUrl(widgetRePopOp.get().getImgName()))
                      );
        } else if (widgetPopOp.isPresent()) {
            return Optional.of(WidgetDto.Response.createPopWidget(
                    widgetPopOp.get(), findImgService.generatePopImgDownloadUrl(widgetPopOp.get().getImgName())));
        } else {
            return Optional.of(WidgetDto.Response.createRePopWidget(
                    widgetRePopOp.get(), findImgService.generateRePopImgDownloadUrl(widgetRePopOp.get().getImgName())));
        }
    }
}
