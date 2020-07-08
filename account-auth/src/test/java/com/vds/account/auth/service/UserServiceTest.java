package com.vds.account.auth.service;

import com.vds.account.auth.domain.User;
import com.vds.account.auth.repository.UserRepository;
import com.vds.account.auth.util.factory.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @BeforeEach
    public void setup() { initMocks(this); }

    @AfterEach
    public void clearDb() { repository.deleteAll(); }

    private static Stream<Arguments> provideCommonUsers() {
        return Stream.of(
            Arguments.of(UserFactory.createUser("name1@gmail.com", "password", new Date())),
            Arguments.of(UserFactory.createUser("name2@gmail.com", "password", new Date()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonUsers")
    @DisplayName("Saves user by given user data")
    public void shouldCreateUser(User user) {
        // when
        when(repository.saveAndFlush(user)).thenReturn(user);
        User savedUser = userService.create(user);

        // then
        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());

        assertNotNull(savedUser.getUsername());
        assertNotNull(savedUser.getLastLogin());

        verify(repository, times(1)).saveAndFlush(user);
    }

    private static Stream<Arguments> provideUpdatedUserList() {
        return Stream.of(
                Arguments.of(UserFactory.createUser("name1@gmail.com", "password", new Date())),
                Arguments.of(UserFactory.createUser("name2@gmail.com", "password", new Date()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideUpdatedUserList")
    @DisplayName("Updates user by received changes")
    public void shouldSaveChangesWhenUpdatedUserGiven(User user) {
        // given
        // when
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.saveAndFlush(user)).thenReturn(user);

        User savedUser = userService.saveChanges(user);

        // then
        assertNotNull(savedUser);
        assertEquals(savedUser.getUsername(), user.getUsername());

        verify(repository, times(1)).saveAndFlush(user);
    }


}