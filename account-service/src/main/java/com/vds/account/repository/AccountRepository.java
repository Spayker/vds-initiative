package com.vds.account.repository;

import com.vds.account.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

	List<Account> findByName(String name);

	Account findByEmail(String email);

	List<Account> findByCreatedDate(Date createDate);

	List<Account> findByModifiedDate(Date modifiedDate);

}
