package com.vds.account.repository;

import com.vds.account.domain.Account;
import com.vds.account.util.factory.AccountFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.vds.account.domain.Gender.FEMALE;
import static com.vds.account.domain.Gender.MALE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository repository;

	@AfterEach
	public void clearRecordsInDb(){
		repository.deleteAll();
	}

	private static Stream<Arguments> provideCommonAccounts() {
		return Stream.of(
			Arguments.of(AccountFactory.createAccount("name1", "name1@gmail.com", new Date(), null, 25, MALE, 75, 173)),
			Arguments.of(AccountFactory.createAccount("name2", "name2@gmail.com", new Date(), null, 35, FEMALE, 48, 165))
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
		assertEquals(account.getAge(), savedAccount.getAge());
		assertEquals(account.getEmail(), savedAccount.getEmail());
		assertEquals(account.getGender(), savedAccount.getGender());
		assertEquals(account.getHeight(), savedAccount.getHeight());
		assertEquals(account.getWeight(), savedAccount.getWeight());
	}

	@ParameterizedTest
	@MethodSource("provideCommonAccounts")
	@DisplayName("Returns saved accounts by their ids")
	public void shouldFindAccountById(Account account) {
		// when
		Account savedAccount = repository.save(account);
		Optional<Account> foundEntity = repository.findById(account.getId());

		// then
		assertNotNull(savedAccount);
		assertTrue(foundEntity.isPresent());
		Account foundAccount = foundEntity.get();
		assertEquals(account.getCreatedDate(), foundAccount.getCreatedDate());
		assertEquals(account.getModifiedDate(), foundAccount.getModifiedDate());
		assertEquals(account.getName(), foundAccount.getName());
		assertEquals(account.getAge(), foundAccount.getAge());
		assertEquals(account.getEmail(), foundAccount.getEmail());
		assertEquals(account.getGender(), foundAccount.getGender());
		assertEquals(account.getHeight(), foundAccount.getHeight());
		assertEquals(account.getWeight(), foundAccount.getWeight());
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
		assertEquals(account.getAge(), foundAccount.getAge());
		assertEquals(account.getEmail(), foundAccount.getEmail());
		assertEquals(account.getGender(), foundAccount.getGender());
		assertEquals(account.getHeight(), foundAccount.getHeight());
		assertEquals(account.getWeight(), foundAccount.getWeight());
	}

	private static Stream<Arguments> provideSameNameAccountList() {
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name", "name1@gmail.com", new Date(), null, 25, MALE, 75, 173),
				AccountFactory.createAccount("name", "name2@gmail.com", new Date(), null, 35, FEMALE, 48, 165));
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
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
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
		assertEquals(account.getAge(), 			foundAccount.getAge());
		assertEquals(account.getEmail(), 		foundAccount.getEmail());
		assertEquals(account.getGender(), 		foundAccount.getGender());
		assertEquals(account.getHeight(), 		foundAccount.getHeight());
		assertEquals(account.getWeight(), 		foundAccount.getWeight());
	}

	private static Stream<Arguments> provideSameCreateDateAccountList() {
		final Date createdDate = new Date();
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name@gmail.com", createdDate, null, 25, MALE, 75, 173),
				AccountFactory.createAccount("name2", "name@gmail.com", createdDate, null, 35, FEMALE, 48, 165));
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
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	private static Stream<Arguments> provideSameModifiedDateAccountList() {
		final Date modifiedDate = new Date();
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name@gmail.com", null, modifiedDate, 25, MALE, 75, 173),
				AccountFactory.createAccount("name2", "name@gmail.com", null, modifiedDate, 35, FEMALE, 48, 165));
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
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	private static Stream<Arguments> provideSameAgeAccountList() {
		final int age = 35;
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name@gmail.com", new Date(), null, age, MALE, 75, 173),
				AccountFactory.createAccount("name2", "name@gmail.com", new Date(), null, age, FEMALE, 48, 165));
		return Stream.of(Arguments.of(accountList));
	}

	@ParameterizedTest
	@MethodSource("provideSameAgeAccountList")
	@DisplayName("Returns saved accounts by their age")
	public void shouldFindAccountsByAge(List<Account> accounts){
		// given
		final int expectedAge = accounts.get(0).getAge();

		// when
		accounts.forEach(repository::save);
		List<Account> foundAccounts = repository.findByAge(expectedAge);

		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	private static Stream<Arguments> provideSameWeightAccountList() {
		final int weight = 75;
		List<Account> accountList = List.of(
			AccountFactory.createAccount("name1", "name@gmail.com", new Date(), null, 25, MALE, weight, 173),
			AccountFactory.createAccount("name2", "name@gmail.com", new Date(), null, 35, FEMALE, weight, 165));
		return Stream.of(Arguments.of(accountList));
	}

	@ParameterizedTest
	@MethodSource("provideSameWeightAccountList")
	@DisplayName("Returns saved accounts by their weight")
	public void shouldFindAccountsByWeight(List<Account> accounts){
		// given
		final int expectedWeight = accounts.get(0).getWeight();

		// when
		accounts.forEach(repository::save);
		List<Account> foundAccounts = repository.findByWeight(expectedWeight);

		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

	private static Stream<Arguments> provideSameHeightAccountList() {
		final int height = 170;
		List<Account> accountList = List.of(
				AccountFactory.createAccount("name1", "name@gmail.com", new Date(), null, 25, MALE, 75, height),
				AccountFactory.createAccount("name2", "name@gmail.com", new Date(), null, 35, FEMALE, 48, height));
		return Stream.of(Arguments.of(accountList));
	}


	@ParameterizedTest
	@MethodSource("provideSameHeightAccountList")
	@DisplayName("Returns saved accounts by their height")
	public void shouldFindAccountsByHeight(List<Account> accounts){
		// given
		final int expectedHeight = accounts.get(0).getHeight();

		// when
		accounts.forEach(repository::save);
		List<Account> foundAccounts = repository.findByHeight(expectedHeight);

		// then
		assertNotNull(foundAccounts);
		assertEquals(accounts.size(), foundAccounts.size());

		for(int i = 0; i != accounts.size(); i++){
			assertEquals(accounts.get(i).getName(), 		foundAccounts.get(i).getName());
			assertEquals(accounts.get(i).getEmail(), 		foundAccounts.get(i).getEmail());
			assertEquals(accounts.get(i).getAge(), 		 	foundAccounts.get(i).getAge());
			assertEquals(accounts.get(i).getGender(), 		foundAccounts.get(i).getGender());
			assertEquals(accounts.get(i).getWeight(), 		foundAccounts.get(i).getWeight());
			assertEquals(accounts.get(i).getHeight(), 		foundAccounts.get(i).getHeight());
			assertEquals(accounts.get(i).getCreatedDate(),  foundAccounts.get(i).getCreatedDate());
			assertEquals(accounts.get(i).getModifiedDate(), foundAccounts.get(i).getModifiedDate());
		}
	}

}
