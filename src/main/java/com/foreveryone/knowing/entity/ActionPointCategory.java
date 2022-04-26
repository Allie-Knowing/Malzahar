package com.foreveryone.knowing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "action_point_category")
@Entity
@Getter
@Setter
public class ActionPointCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "score", nullable = false)
    private Integer score;

}