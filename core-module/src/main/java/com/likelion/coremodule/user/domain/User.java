package com.likelion.coremodule.user.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_user")
@Access(AccessType.FIELD)
public class User extends BaseEntity {
    private static final String DEFAULT_NICKNAME = "사용자";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String subId;


    public static User createSocialUser(String sub) {

        User user = new User();
        user.subId = sub;
        return user;
    }
}