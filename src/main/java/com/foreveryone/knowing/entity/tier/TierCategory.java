package com.foreveryone.knowing.entity.tier;

import lombok.*;

import javax.persistence.*;

@Table(name = "tier_category")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer nextTotIq;

    @Column(nullable = false)
    private Integer nextAdoptionCnt;

    @Column(length = 2000, nullable = false)
    private String imageUrl;

}