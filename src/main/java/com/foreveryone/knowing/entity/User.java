package com.foreveryone.knowing.entity;

import com.foreveryone.knowing.error.exceptions.ProviderDoesNotMatchException;
import com.foreveryone.knowing.oauth.OauthProvider;
import lombok.*;

import javax.persistence.*;
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

    @Column(length = 2000, nullable = false)
    private String profile;

    @Column(length = 30, nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

    public void checkProvider(OauthProvider provider) {
        if (!this.provider.equals(provider)) {
            throw new ProviderDoesNotMatchException("이미 다른 방법으로 가입하셨네요? " + this.provider + " 계정으로 로그인해보세요!");
        }
    }
}
