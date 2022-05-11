package com.foreveryone.knowing.entity.mypage;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FollowId implements Serializable {

    @Column()
    private Integer follower;

    private Integer following;
}
