package com.popping.domain.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaveCustomFriendGroupDto {
    private String groupName;
    private List<Long> friendPks;
}
