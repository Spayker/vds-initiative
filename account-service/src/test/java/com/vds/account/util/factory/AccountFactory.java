package com.vds.account.util.factory;

import com.vds.account.domain.Account;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;


public class AccountFactory {

    public static Account createDummyAccount(){
        return Account.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .email(RandomStringUtils.randomAlphabetic(6) + "@somemail.com")
                .createdDate(new Date())
                .modifiedDate(null)
                .build();
    }

    public static Account createAccount(String name,
                                        String email,
                                        Date createDate,
                                        Date modifiedDate) {
        return Account.builder()
                .name(name)
                .email(email)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}
