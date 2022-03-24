package com.foreveryone.knowing.entity;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Clob;

@Table(name = "report")
@Entity
@Getter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description")
    private Clob description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

}