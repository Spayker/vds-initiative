package com.vds.account.controller;

import com.vds.account.domain.Account;
import com.vds.account.domain.User;
import com.vds.account.dto.AccountDto;
import com.vds.account.dto.mapper.AccountMapper;
import com.vds.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountMapper accountMapper;

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getAccountByName(@PathVariable String name) {
		return new ResponseEntity<>(accountService.findAccountByName(name), HttpStatus.OK);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public ResponseEntity<Account> createNewAccount(@Valid @RequestBody AccountDto accountDto) {
		Account account = accountMapper.accountDtoToAccount(accountDto);
		User user = accountMapper.accountDtoToUser(accountDto);
		return new ResponseEntity<>(accountService.create(account, user), HttpStatus.CREATED);
	}

}
