package com.vds.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  Simple DTO that was created to map controller incoming data from model layer.
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

    private long id;

    private String name;

    private String email;

    private String password;

}
