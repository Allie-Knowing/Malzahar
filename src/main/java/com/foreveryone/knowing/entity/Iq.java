package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Table(name = "iq")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Iq {
    @Id
    private Integer id;

    @OneToOne(optional = false, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "cur_cnt", nullable = false)
    private BigInteger curCnt;

    @Column(name = "tot_cnt", nullable = false)
    private BigInteger totCnt;

}