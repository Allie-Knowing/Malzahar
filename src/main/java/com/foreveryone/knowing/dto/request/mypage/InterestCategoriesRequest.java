package com.foreveryone.knowing.dto.request.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InterestCategoriesRequest {
    private List<Integer> interestCategories;
}
