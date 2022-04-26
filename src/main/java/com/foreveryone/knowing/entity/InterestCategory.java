package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "interest_category")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

}