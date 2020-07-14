package com.vds.account.repository;

import com.vds.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 *  Looks for account by provided name.
	 *  @param name - string value, used during account search by name
	 **/
	List<Account> findByName(String name);

	/**
	 *  Looks for account by provided email.
	 *  @param email - string value, used during account search by email
	 **/
	Account findByEmail(String email);

}
