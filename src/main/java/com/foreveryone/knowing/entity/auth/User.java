package com.foreveryone.knowing.entity.auth;

import com.foreveryone.knowing.entity.admin.inquiry.Inquiry;
import com.foreveryone.knowing.entity.admin.Report;
import com.foreveryone.knowing.error.exceptions.ProviderDoesNotMatchException;
import com.foreveryone.knowing.oauth.utils.OauthProvider;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private OauthProvider provider;

    @Column(length = 2000)
    private String profile;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "last_accessed_at", nullable = false)
    private Timestamp lastAccessedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Inquiry> inquiries = new ArrayList<>();

    public void checkProvider(OauthProvider provider) {
        if (!this.provider.equals(provider)) {
            throw new ProviderDoesNotMatchException("이미 다른 방법으로 가입하셨네요? " + this.provider + " 계정으로 로그인해보세요!");
        }
    }

    public void updateNickname(String nickname) {
        this.name = nickname;
    }
}
