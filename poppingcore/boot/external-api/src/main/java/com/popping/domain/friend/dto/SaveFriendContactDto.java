package com.popping.domain.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SaveFriendContactDto {
    @Getter
    @NoArgsConstructor
    public static class Request {
        private List<String> phoneNumbers;

        public List<String> filter(String ownerPhoneNumber) {
            return phoneNumbers.stream()
                    .filter(phoneNum -> phoneNum != null && !phoneNum.isBlank() && !phoneNum.equals(ownerPhoneNumber))
                    .distinct()
                    .toList();
        }
    }
}
