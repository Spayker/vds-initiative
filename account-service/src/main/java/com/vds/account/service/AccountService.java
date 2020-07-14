package com.vds.account.service;

import com.vds.account.domain.Account;
import com.vds.account.domain.User;

import java.util.List;

public interface AccountService {

	/**
	 *  Looks for stored account by its id
	 *  @param accountId - string value for search
	 *  @return found Account
	 **/
	Account findAccountById(String accountId);

	/**
	 *  Looks for stored account by its name.
	 *  @param accountName - string value for search
	 *  @return found Account
	 **/
	List<Account> findAccountByName(String accountName);

	/**
	 *  Looks for stored account by its email.
	 *  @param email - string value for search
	 *  @return found Account
	 **/
	Account findAccountByEmail(String email);

	/**
	 *  Creates new Account and returns it by provided User instance.
	 *  @param user - instance of User with email and password
	 *  @return created Account
	 **/
	Account create(Account account, User user);

	/**
	 *  Updates a stored account and returns its updated variant.
	 *  @param update - an updated variation of Account that must be persisted
	 **/
	Account saveChanges(Account update);
}
