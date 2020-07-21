package com.vds.account.service;

import com.vds.account.client.AuthServiceClient;
import com.vds.account.domain.Account;
import com.vds.account.domain.User;
import com.vds.account.exception.AccountException;
import com.vds.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 *  Service layer implementation to work with Account entities.
 **/
@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthServiceClient authClient;

	@Autowired
	private AccountRepository repository;

	/**
	 *  Looks for stored account by its name.
	 *  @param accountName - string value for search
	 *  @return found Account
	 **/
	@Override
	public Account findByName(String accountName) {
		if(accountName.length() == 0){
			throw new IllegalArgumentException("provided accountName has 0 String length");
		}
		return repository.findByName(accountName);
	}

	/**
	 *  Looks for stored account by its id
	 *  @param accountId - string value for search
	 *  @return found Account
	 **/
	@Override
	public Account findById(String accountId) {
		if(accountId.length() == 0){
			throw new IllegalArgumentException("provided accountId has 0 String length");
		}
		Optional<Account> foundAccount = repository.findById(accountId);
		return foundAccount.orElse(null);
	}

	/**
	 *  Creates new Account and returns it by provided User instance.
	 *  @param user - instance of User with email and password
	 *  @return created Account
	 **/
	@Override
	public Account create(User user) {
		Account existing = repository.findByName(user.getUsername());
		if(existing == null){
			authClient.createUser(user);
			Account account = Account.builder()
					.name(user.getUsername())
					.lastSeen(new Date())
					.build();

			repository.save(account);
			log.info("new account has been created: " + account.getName());
			return account;
		} else {
			throw new AccountException("account already exists: " + user.getUsername());
		}
	}

	/**
	 *  Updates a stored account and returns its updated variant.
	 *  @param name - String value to search a target account for update
	 *  @param update - an updated variation of Account that must be persisted
	 **/
	@Override
	public void saveChanges(String name, Account update) {
		Account account = repository.findByName(name);
		if(account == null){
			throw new AccountException("can't find account with name " + name);
		} else {
			account.setLastSeen(new Date());
			account.setDeviceIds(update.getDeviceIds());
			repository.save(account);
			log.debug("account {} changes has been saved", name);
		}
	}
}
