package com.foreveryone.knowing.entity.mypage;

import com.foreveryone.knowing.entity.auth.User;
import lombok.*;

import javax.persistence.*;

@Table(name = "follow")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {
    @EmbeddedId
    private FollowId followId;

    @MapsId("follower")
    @ManyToOne
    @JoinColumn(name = "follower", nullable = false)
    private User follower;

    @MapsId("following")
    @ManyToOne
    @JoinColumn(name = "following", nullable = false)
    private User following;
}