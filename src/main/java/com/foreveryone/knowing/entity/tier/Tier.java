package com.foreveryone.knowing.entity.tier;

import com.foreveryone.knowing.entity.auth.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "tier")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tier {
    @Id
    private Integer id;

    @MapsId
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private TierCategory tierCategory;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}