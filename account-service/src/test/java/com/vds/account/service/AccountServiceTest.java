package com.vds.account.service;

import com.vds.account.client.AuthServiceClient;
import com.vds.account.domain.Account;
import com.vds.account.domain.User;
import com.vds.account.dto.mapper.AccountMapper;
import com.vds.account.repository.AccountRepository;
import com.vds.account.util.factory.AccountFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AuthServiceClient authClient;

    @BeforeEach
    public void setup() { initMocks(this); }

    @AfterEach
    public void clearDb() { repository.deleteAll(); }

    private static Stream<Arguments> provideCommonAccounts() {
        return Stream.of(
                Arguments.of(AccountFactory.createAccount("name1", "name1@gmail.com", new Date(), null)),
                Arguments.of(AccountFactory.createAccount("name2", "name2@gmail.com", new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Saves account by given account data")
    public void shouldCreateAccount(Account account) {
        // given
        User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        // when
        when(repository.save(account)).thenReturn(account);
        Account savedAccount = accountService.create(account, user);

        // then
        assertNotNull(savedAccount);
        assertEquals(account.getName(), savedAccount.getName());

        assertNotNull(savedAccount.getCreatedDate());
        assertNull(savedAccount.getModifiedDate());

        verify(repository, times(1)).save(account);
    }

    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Updates account by received changes")
    public void shouldSaveChangesWhenUpdatedAccountGiven(Account account) {
        // given
        final User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        final String updatePrefix = "_updated";

        // when
        when(repository.findByEmail(account.getEmail())).thenReturn(null);
        when(repository.save(account)).thenReturn(account);
        Account createdAccount = accountService.create(account, user);

        createdAccount.setName(createdAccount.getName() + updatePrefix);

        when(repository.findByEmail(createdAccount.getEmail())).thenReturn(createdAccount);
        Account updatedAccount = accountService.saveChanges(createdAccount);

        // then
        assertNotNull(updatedAccount);
        assertEquals(updatedAccount.getName(), 	 createdAccount.getName());

        verify(repository, times(2)).save(account);
    }


    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Looks for account by name")
    public void shouldFindAccountByName(Account account) {
        // given
        final User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();
        final int expectedFoundAccounts = 1;

        // when
        when(repository.save(account)).thenReturn(account);
        Account savedAccount = accountService.create(account, user);
        when(repository.findByName(account.getName())).thenReturn(List.of(savedAccount));
        List<Account> foundAccounts = accountService.findAccountByName(account.getName());

        assertEquals(expectedFoundAccounts, foundAccounts.size());
        assertEquals(foundAccounts.get(0).getName(), savedAccount.getName());
        assertEquals(foundAccounts.get(0).getEmail(), savedAccount.getEmail());
        assertEquals(foundAccounts.get(0).getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(foundAccounts.get(0).getModifiedDate(), savedAccount.getModifiedDate());
    }

    private static Stream<Arguments> provideSameNameAccountList() {
        final String name = "name";
        List<Account> accountList = List.of(
                AccountFactory.createAccount(name, "name1@gmail.com", new Date(), null),
                AccountFactory.createAccount(name, "name2@gmail.com", new Date(), null));
        return Stream.of(Arguments.of(accountList));
    }

    @ParameterizedTest
    @MethodSource("provideSameNameAccountList")
    @DisplayName("Looks for accounts by name")
    public void shouldFindAccountByName(List<Account> accounts) {
        // given
        final int expectedFoundAccounts = 2;

        // when
        when(repository.findByName(accounts.get(0).getName())).thenReturn(accounts);

        List<Account> foundAccounts = accountService.findAccountByName(accounts.get(0).getName());

        assertEquals(expectedFoundAccounts, foundAccounts.size());
        assertEquals(foundAccounts.get(0).getName(), accounts.get(0).getName());
        assertEquals(foundAccounts.get(1).getName(), accounts.get(1).getName());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Throws IllegalArgumentException when looks for account by empty string name")
    public void shouldFailWhenFindAccountByNameWithEmptyNameValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.findAccountByName(""));
    }

    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Looks for account by email")
    public void shouldFindAccountByEmail(Account account) {
        // given
        final User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();
        when(repository.save(account)).thenReturn(account);
        Account savedAccount = accountService.create(account, user);

        // when
        when(repository.findByEmail(account.getEmail())).thenReturn(savedAccount);
        Account foundAccount = accountService.findAccountByEmail(account.getEmail());

        assertNotNull(foundAccount);
        assertEquals(foundAccount.getName(), savedAccount.getName());
        assertEquals(foundAccount.getEmail(), savedAccount.getEmail());
        assertEquals(foundAccount.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(foundAccount.getModifiedDate(), savedAccount.getModifiedDate());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Throws IllegalArgumentException when looks for account by empty string email")
    public void shouldFailWhenFindAccountByEmailWithEmptyEmailValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.findAccountByEmail(""));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for account by blank string email")
    public void shouldFailWhenFindAccountByEmailWithBlankEmailValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.findAccountByEmail("     "));
    }

    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Looks for account by created date")
    public void shouldFindAccountByCreatedDate(Account account) {
        // given
        final User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();
        final int expectedFoundAccounts = 1;
        when(repository.save(account)).thenReturn(account);
        Account savedAccount = accountService.create(account, user);

        // when
        when(repository.findByCreatedDate(account.getCreatedDate())).thenReturn(List.of(savedAccount));
        List<Account> foundAccounts = accountService.findAccountByCreatedDate(account.getCreatedDate());

        assertNotNull(foundAccounts);
        assertEquals(expectedFoundAccounts, foundAccounts.size());

        assertEquals(foundAccounts.get(0).getName(), savedAccount.getName());
        assertEquals(foundAccounts.get(0).getEmail(), savedAccount.getEmail());
        assertEquals(foundAccounts.get(0).getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(foundAccounts.get(0).getModifiedDate(), savedAccount.getModifiedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonAccounts")
    @DisplayName("Looks for account by modified date")
    public void shouldFindAccountByModifiedDate(Account account) {
        // given
        final User user = User.builder().
                username(account.getName())
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();
        final int expectedFoundAccounts = 1;
        when(repository.save(account)).thenReturn(account);
        Account savedAccount = accountService.create(account, user);

        // when
        when(repository.findByModifiedDate(account.getModifiedDate())).thenReturn(List.of(savedAccount));
        List<Account> foundAccounts = accountService.findAccountByModifiedDate(account.getModifiedDate());

        assertNotNull(foundAccounts);
        assertEquals(expectedFoundAccounts, foundAccounts.size());

        assertEquals(foundAccounts.get(0).getName(), savedAccount.getName());
        assertEquals(foundAccounts.get(0).getEmail(), savedAccount.getEmail());
        assertEquals(foundAccounts.get(0).getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(foundAccounts.get(0).getModifiedDate(), savedAccount.getModifiedDate());
    }

}