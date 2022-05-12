package com.foreveryone.knowing.entity.admin.answer;

import com.foreveryone.knowing.entity.auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "comment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Timestamp deletedAt;

    public void softDelete() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
    }

}