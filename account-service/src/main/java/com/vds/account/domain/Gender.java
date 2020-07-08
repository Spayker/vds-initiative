package com.vds.account.domain;

public enum Gender {

    FEMALE(0),
    MALE(1);

    private final int value;

    Gender(int i) {
        value = i;
    }

    public int getValue(){
        return value;
    }
}
