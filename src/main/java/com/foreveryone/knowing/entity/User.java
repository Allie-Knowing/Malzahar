package com.foreveryone.knowing.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 320, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String provider;

    @Column(length = 2000, nullable = false)
    private String profile;

    @Column(length = 30, nullable = false)
    private String name;
}
