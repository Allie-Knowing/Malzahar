package com.foreveryone.knowing.dto.response.mypage;

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

    public static InterestResponse of(InterestCategory interestCategory) {
        return InterestResponse.builder()
                .interestId(interestCategory.getId())
                .interest(interestCategory.getName())
                .build();
    }
}
