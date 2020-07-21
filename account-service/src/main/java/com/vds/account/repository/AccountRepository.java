package com.vds.account.repository;

import com.vds.account.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

	/**
	 *  Looks for account by provided name.
	 *  @param name - string value, used during account search by name
	 **/
	Account findByName(String name);

}
