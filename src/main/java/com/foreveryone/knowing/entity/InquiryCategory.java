package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "inquiry_category")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Enumerated
    @Column(name = "category", nullable = false)
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }
}