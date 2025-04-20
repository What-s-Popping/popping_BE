package com.popping.domain.widget.dto;

import com.popping.data.pop.chip.ColorChip;
import com.popping.data.pop.entity.BasePop;
import com.popping.domain.pop.dto.poptype.PopType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class WidgetDto {
    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String imgUrl;
        private String contents;
        private String chip;
        private ColorChip colorChip;
        private PopType popType;

        @Builder(access = AccessLevel.PRIVATE)
        private Response(String chip, ColorChip colorChip, String contents, Long id, String imgUrl, PopType popType) {
            this.chip = chip;
            this.colorChip = colorChip;
            this.contents = contents;
            this.id = id;
            this.imgUrl = imgUrl;
            this.popType = popType;
        }

        public static Response createPopWidget(BasePop pop, String imgUrl) {
            return Response.builder()
                    .chip(pop.getChip())
                    .colorChip(pop.getColorChip())
                    .contents(pop.getContents())
                    .id(pop.getPk())
                    .imgUrl(imgUrl)
                    .popType(PopType.POP)
                    .build();
        }

        public static Response createRePopWidget(BasePop pop, String imgUrl) {
            return Response.builder()
                    .chip(pop.getChip())
                    .colorChip(pop.getColorChip())
                    .contents(pop.getContents())
                    .id(pop.getPk())
                    .imgUrl(imgUrl)
                    .popType(PopType.RE_POP)
                    .build();
        }
    }
}
