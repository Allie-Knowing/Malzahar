package com.foreveryone.knowing.entity.actionpoint;

import com.foreveryone.knowing.entity.auth.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "action_point")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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