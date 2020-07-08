package com.vds.account.auth.repository;

import com.vds.account.auth.domain.User;
import com.vds.account.auth.util.factory.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @AfterEach
    public void clearRecordsInDb(){
        repository.deleteAll();
    }

    private static Stream<Arguments> provideCommonUsers() {
        return Stream.of(
            Arguments.of(UserFactory.createUser("name1@gmail.com", "password", new Date())),
            Arguments.of(UserFactory.createUser("name2@gmail.com", "password", new Date()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonUsers")
    @DisplayName("Saves users by repository API")
    public void shouldSaveUser(User user){
        // when
        User savedUser = repository.saveAndFlush(user);

        // then
        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getLastLogin(), savedUser.getLastLogin());
    }

    @ParameterizedTest
    @MethodSource("provideCommonUsers")
    @DisplayName("Returns saved users by their username")
    public void shouldFindAccountByEmail(User user) {
        // given
        // when
        repository.save(user);
        User foundUser = repository.findByUsername(user.getUsername());

        // then
        assertEquals(user.getId(), 		     foundUser.getId());
        assertEquals(user.getUsername(),     foundUser.getUsername());
        assertEquals(user.getPassword(),     foundUser.getPassword());
        assertEquals(user.getLastLogin(),    foundUser.getLastLogin());
    }


}
