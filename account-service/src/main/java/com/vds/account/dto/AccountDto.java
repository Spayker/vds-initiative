package com.vds.account.dto;

import com.vds.account.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

    private long id;

    private String name;

    private String email;

    private String password;

    private Date createdDate;

    private Date modifiedDate;

    private int age;

    private Gender gender;

    private int weight;

    private int height;



}
