package com.foreveryone.knowing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "action_point")
@Entity
@Getter
@Setter
public class ActionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ActionPointCategory actionPointCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}