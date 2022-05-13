package com.foreveryone.knowing.entity.admin.answer;

import com.foreveryone.knowing.entity.auth.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "video_url", nullable = false, length = 2000)
    private String videoUrl;

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Video softDelete() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
        return this;
    }
}