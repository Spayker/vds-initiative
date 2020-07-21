package com.vds.account.client;

import com.vds.account.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  A feign client interface that allows to communicate with other micro-service APP a server side has in its arsenal.
 *  This one was created to communicate with auth service when a new account is going to be created.
 **/
@FeignClient(name = "account-auth")
public interface AuthServiceClient {

	/**
	 *  Sends create user request to auth service.
	 *  @param user an instance of user with login and password (plus additional utility fields)
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "/mservicet/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void createUser(User user);

}
