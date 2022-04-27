package com.foreveryone.knowing.entity.actionpoint;

import lombok.*;

import javax.persistence.*;

@Table(name = "action_point_category")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionPointCategory {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "score", nullable = false)
    private Integer score;

}