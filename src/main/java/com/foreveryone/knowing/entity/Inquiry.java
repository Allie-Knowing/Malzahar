package com.foreveryone.knowing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Clob;

@Table(name = "inquiry")
@Entity
@Getter
@Setter
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private Clob description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}