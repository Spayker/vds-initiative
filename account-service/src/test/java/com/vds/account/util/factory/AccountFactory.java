package com.vds.account.util.factory;

import com.vds.account.domain.Account;
import org.apache.commons.lang.RandomStringUtils;


public class AccountFactory {

    public static Account createDummyAccount(){
        return Account.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

    public static Account createAccount(String name) {
        return Account.builder()
                .name(name)
                .build();
    }
}
