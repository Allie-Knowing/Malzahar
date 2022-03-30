package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "inquiry_category")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "title", nullable = false)
    private Category category;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "inquiryCategory", orphanRemoval = true)
    private Inquiry inquiry;
}