package com.foreveryone.knowing.dto.response.mypage;

import com.foreveryone.knowing.entity.mypage.Interest;
import com.foreveryone.knowing.entity.mypage.InterestCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestResponse {
        private Integer interestId;
        private String interest;

    public static InterestResponse of(Interest interest) {
        return InterestResponse.builder()
                .interestId(interest.getCategory().getId())
                .interest(interest.getCategory().getName())
                .build();
    }
}
