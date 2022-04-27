package com.foreveryone.knowing.entity.admin.answer;

import com.foreveryone.knowing.entity.auth.User;
import lombok.*;

import javax.persistence.*;

@Table(name = "video")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}