package com.popping.domain.pop.dto;

import com.popping.data.pop.chip.Chip;
import lombok.Builder;
import lombok.Getter;

public class ChipDto {
    @Getter
    public static class Response{
        private final Chip[] chips;

        @Builder
        public Response(Chip[] chips) {
            this.chips = chips;
        }
    }
}
