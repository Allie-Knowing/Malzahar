package com.foreveryone.knowing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "report")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}