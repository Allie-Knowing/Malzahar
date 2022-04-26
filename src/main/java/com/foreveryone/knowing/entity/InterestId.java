package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestId implements Serializable {

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "user_id")
    private Integer userId;
}