package com.popping.domain.friend.dto;

import com.popping.data.friendgroup.entity.FriendGroupMember;
import com.popping.data.friendreqeust.entity.FriendRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FindFriendDto {
    @Getter
    public static class Response{
        private final List<FriendInfo> friendList;

        @Builder(access = AccessLevel.PRIVATE)
        private Response(List<FriendInfo> friendList) {
            this.friendList = friendList;
        }
        public static Response fromFriendGroupMember(List<FriendGroupMember> friendGroupMembers) {
            return Response.builder()
                    .friendList(friendGroupMembers.stream()
                            .map(friendGroupMember -> FriendInfo.builder()
                                    .id(friendGroupMember.getMember().getPk())
                                    .name(friendGroupMember.getMember().getName())
                                    .build())
                            .toList())
                    .build();
        }
        public static Response fromFriendRequest(List<FriendRequest> friendRequests) {
            return Response.builder()
                    .friendList(friendRequests.stream()
                            .map(friendRequest->FriendInfo.builder()
                                    .id(friendRequest.getPk())
                                    .name(friendRequest.getFromMember().getName())
                                    .build())
                            .toList())
                    .build();
        }
    }

    private static class FriendInfo{
        private final String name;
        private final Long id;

        @Builder
        public FriendInfo(String name, Long id) {
            this.name = name;
            this.id = id;
        }
    }
}
