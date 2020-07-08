package com.vds.account.auth.util.factory;

import com.vds.account.auth.domain.User;

import java.util.Date;

public class UserFactory {


    public static User createUser(String name,
                                  String password,
                                  Date lastLogin) {
        return User.builder()
                .username(name)
                .password(password)
                .lastLogin(lastLogin)
                .build();
    }

    public static User createUser(Long id,
                                  String name,
                                  String password,
                                  Date lastLogin) {
        return User.builder()
                .id(id)
                .username(name)
                .password(password)
                .lastLogin(lastLogin)
                .build();
    }
}
