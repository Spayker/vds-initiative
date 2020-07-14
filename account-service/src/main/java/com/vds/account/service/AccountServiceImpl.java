package com.vds.account.service;

import com.vds.account.client.AuthServiceClient;
import com.vds.account.domain.Account;
import com.vds.account.domain.User;
import com.vds.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountRepository repository;

	@Autowired
	private AuthServiceClient authClient;

	/**
	 *  Looks for stored account by its name.
	 *  @param name - string value for search
	 *  @return found Account
	 **/
	@Override
	public List<Account> findAccountByName(String name) {
		if(name.isEmpty() || name.isBlank()){
			throw new IllegalArgumentException("provided name is empty or blank");
		}
		return repository.findByName(name);
	}

	/**
	 *  Looks for stored account by its id
	 *  @param accountId - string value for search
	 *  @return found Account
	 **/
	@Override
	public Account findAccountById(String accountId) {
		if(accountId.isEmpty() || accountId.isBlank()){
			throw new IllegalArgumentException("provided accountId has 0 String length");
		}
		Optional<Account> foundAccount = repository.findById(Long.valueOf(accountId));
		return foundAccount.orElse(null);
	}

	@Override
	public Account findAccountByEmail(String email) {
		/*if(email.isEmpty() || email.isBlank()){
			throw new IllegalArgumentException("provided email is empty or blank");
		}
		return repository.findByEmail(email);*/
		return null;
	}

	@Override
	public Account create(Account account, User user) {
		/*Account existing = repository.findByEmail(account.getEmail());
		if(existing == null){
			authClient.createUser(user);
			Account savedAccount = repository.saveAndFlush(account);
			log.info("new account has been created: " + savedAccount.getEmail());
			return savedAccount;
		} else {
			throw new AccountException("account already exists: " + account.getEmail());
		}*/
		return null;
	}

	@Override
	public Account saveChanges(Account update) {
		/*Account account = repository.findByEmail(update.getEmail());
		if(account == null){
			throw new AccountException("can't find account with email " + update.getEmail());
		} else {
			update.setModifiedDate(new Date());
			log.debug("account {} changes have been saved", update.getEmail());
			return repository.saveAndFlush(update);
		}*/
		return null;
	}
}
