package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "interest")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interest {
    @EmbeddedId
    private InterestId interestId;

    @MapsId("categoryId")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private InterestCategory category;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}