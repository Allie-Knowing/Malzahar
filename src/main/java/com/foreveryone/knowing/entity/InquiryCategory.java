package com.foreveryone.knowing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "inquiry_category")
@Entity
@Getter
@Setter
public class InquiryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private Inquiry inquiry;

}