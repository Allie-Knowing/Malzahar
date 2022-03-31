package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "inquiry")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "inquiry_category_id", nullable = false)
    private InquiryCategory inquiryCategory;

}