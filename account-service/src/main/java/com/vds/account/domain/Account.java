package com.vds.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *  Account model entity that contains major information about account.
 **/
@Document(collection = "accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {


	private String name;

	@Id
	private String email;

	private Date createdDate;

	private Date modifiedDate;

}
