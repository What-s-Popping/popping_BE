package com.popping.domain.friend.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class FindCustomFriendGroupDto {
    private String groupName;
    private long groupId;
    private List<FriendGroupMember> groupMembers;

    @Builder
    public FindCustomFriendGroupDto(String groupName, long groupId, List<FriendGroupMember> groupMembers) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembers = groupMembers;
    }

    public static class FriendGroupMember {
        private long userId;
        private String userName;
        private String profileImgUrl;

        @Builder
        public FriendGroupMember(long userId, String userName, String profileImgUrl) {
            this.userId = userId;
            this.userName = userName;
            this.profileImgUrl = profileImgUrl;
        }
    }
}
