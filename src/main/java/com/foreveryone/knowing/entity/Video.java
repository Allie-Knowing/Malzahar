package com.foreveryone.knowing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Clob;
import java.sql.Timestamp;

@Table(name = "video")
@Entity
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "description", nullable = false)
    private Clob description;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "id_adoption", nullable = false)
    private boolean idAdoption;

}