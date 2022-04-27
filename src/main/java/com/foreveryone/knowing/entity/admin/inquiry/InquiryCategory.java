package com.foreveryone.knowing.entity.admin.inquiry;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "title", nullable = false)
    private InquiryCategoryEnum inquiryCategoryEnum;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "inquiryCategory", orphanRemoval = true)
    private Inquiry inquiry;
}