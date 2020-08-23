package com.vds.account.repository;

import com.vds.account.domain.Account;
import com.vds.account.util.factory.AccountFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataMongoTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	@AfterEach
	public void clearRecordsInDb(){
		repository.deleteAll();
	}

	private static Stream<Arguments> provideCommonAccounts() {
		return Stream.of(
				Arguments.of(AccountFactory.createAccount("name1", "name1@gmail.com", new Date(), null)),
				Arguments.of(AccountFactory.createAccount("name2", "name2@gmail.com", new Date(), null))
		);
	}

	@ParameterizedTest
	@MethodSource("provideCommonAccounts")
	@DisplayName("Saves accounts by repository API")
	public void shouldSaveAccount(Account account){
		// when
		Account savedAccount = repository.save(account);

		// then
		assertNotNull(savedAccount);
		assertEquals(account.getCreatedDate(), savedAccount.getCreatedDate());
		assertEquals(account.getModifiedDate(), savedAccount.getModifiedDate());
		assertEquals(account.getName(), savedAccount.getName());
		assertEquals(account.getEmail(), savedAccount.getEmail());
	}

	@ParameterizedTest
	@MethodSource("provideCommonAccounts")
	@DisplayName("Returns saved accounts by their names")
	public void shouldFindAccountByName(Account account) {
		// given
		final int expectedFoundAccounts = 1;

		// when
		repository.save(account);
		List<Account> foundAccounts = repository.findByName(account.getName());

		// then
		assertNotNull(foundAccounts);
		assertEquals(expectedFoundAccounts, foundAccounts.size());
		Account foundAccount = foundAccounts.get(0);
		assertEquals(account.getCreatedDate(), foundAccount.getCreatedDate());
		assertEquals(account.getModifiedDate(), foundAccount.getModifiedDate());
		assertEquals(account.getName(), foundAccount.getName());
		assertEquals(account.getEmail(), foundAccount.getEmail());
	}

	private static Stream<Arguments> provideSameNameAccountList() {
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name", "name1@gmail.com", new Date(), null),
				AccountFactory.createAccount("name", "name2@gmail.com", new Date(), null));
		return Stream.of(Arguments.of(accountList));
	}

	@ParameterizedTest
	@MethodSource("provideSameNameAccountList")
	@DisplayName("Returns saved accounts by their names")
	public void shouldFindAccountsByName(List<Account> accounts) {
		// given
		final String name = "name";

		// when
		accounts.forEach(repository::save);

		List<Account> foundAccounts = repository.findByName(name);
		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	@ParameterizedTest
	@MethodSource("provideCommonAccounts")
	@DisplayName("Returns saved accounts by their email")
	public void shouldFindAccountByEmail(Account account) {
		// given
		// when
		repository.save(account);
		Account foundAccount = repository.findByEmail(account.getEmail());

		// then
		assertEquals(account.getCreatedDate(), 	foundAccount.getCreatedDate());
		assertEquals(account.getModifiedDate(), foundAccount.getModifiedDate());
		assertEquals(account.getName(), 		foundAccount.getName());
		assertEquals(account.getEmail(), 		foundAccount.getEmail());
	}

	private static Stream<Arguments> provideSameCreateDateAccountList() {
		final Date createdDate = new Date();
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name1@gmail.com", createdDate, null),
				AccountFactory.createAccount("name2", "name2@gmail.com", createdDate, null));
		return Stream.of(Arguments.of(accountList));
	}

	@ParameterizedTest
	@MethodSource("provideSameCreateDateAccountList")
	@DisplayName("Returns saved accounts by their created date")
	public void shouldFindAccountsByCreatedDate(List<Account> accounts) {
		// given
		final Date expectedCreatedDate = accounts.get(0).getCreatedDate();

		// when
		accounts.forEach(repository::save);
		List<Account> foundAccounts = repository.findByCreatedDate(expectedCreatedDate);

		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	private static Stream<Arguments> provideSameModifiedDateAccountList() {
		final Date modifiedDate = new Date();
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name1@gmail.com", null, modifiedDate),
				AccountFactory.createAccount("name2", "name2@gmail.com", null, modifiedDate));
		return Stream.of(Arguments.of(accountList));
	}

	@ParameterizedTest
	@MethodSource("provideSameModifiedDateAccountList")
	@DisplayName("Returns saved accounts by their modified date")
	public void shouldFindAccountByModifiedDate(List<Account> accounts) {
		// given
		final Date expectedModifiedDate = accounts.get(0).getModifiedDate();

		// when
		accounts.forEach(repository::save);
		List<Account> foundAccounts = repository.findByModifiedDate(expectedModifiedDate);

		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}
}