package com.vds.account.repository;

import com.vds.account.domain.Account;
import com.vds.account.domain.Training;
import com.vds.account.util.factory.AccountFactory;
import com.vds.account.util.factory.TrainingFactory;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TrainingRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @AfterEach
    public void clearRecordsInDb(){
        trainingRepository.deleteAll();
        accountRepository.deleteAll();
    }

    private static Stream<Arguments> provideCommonTrainings() {
        return Stream.of(
            Arguments.of(TrainingFactory.createTraining("miBand3", "v1.0", "jogging", 12000, 3500, new Date(), AccountFactory.createDummyAccount())),
            Arguments.of(TrainingFactory.createTraining("miBand4", "v1.2", "cycling", 12000, 5500, new Date(), AccountFactory.createDummyAccount()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Saves trainings by repository API")
    public void shouldSaveAccount(Training training){
        // given
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);

        // then
        assertNotNull(savedTraining);
        assertEquals(training.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(training.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(training.getType(), savedTraining.getType());
        assertEquals(training.getTime(), savedTraining.getTime());
        assertEquals(training.getCalories(), savedTraining.getCalories());
        assertEquals(training.getAccount(), savedTraining.getAccount());
        assertEquals(training.getCreatedDate(), savedTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their ids")
    public void shouldFindTrainingById(Training training) {
        // given
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        Optional<Training> foundEntity = trainingRepository.findById(savedTraining.getId());

        // then
        assertNotNull(savedTraining);
        assertTrue(foundEntity.isPresent());
        Training foundTraining = foundEntity.get();
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their device name")
    public void shouldFindTrainingByDeviceName(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByDeviceName(savedTraining.getDeviceName());

        // then
        assertNotNull(savedTraining);

        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their device firmware")
    public void shouldFindTrainingByDeviceFirmware(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByDeviceFirmware(savedTraining.getDeviceFirmware());

        // then
        assertNotNull(savedTraining);

        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their callories")
    public void shouldFindTrainingByCalories(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByCalories(savedTraining.getCalories());

        // then
        assertNotNull(savedTraining);

        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved training by its created date")
    public void shouldFindTrainingByCreatedDate(Training training) {
        // given
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        Training foundTraining = trainingRepository.findByCreatedDate(savedTraining.getCreatedDate());

        // then
        assertNotNull(savedTraining);
        assertNotNull(foundTraining);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their time")
    public void shouldFindTrainingByTime(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByTime(savedTraining.getTime());

        // then
        assertNotNull(savedTraining);
        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their type")
    public void shouldFindTrainingByType(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByType(savedTraining.getType());

        // then
        assertNotNull(savedTraining);
        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Returns saved trainings by their account")
    public void shouldFindTrainingByAccount(Training training) {
        // given
        final int expectedFoundTraining = 1;
        Account account = accountRepository.saveAndFlush(AccountFactory.createDummyAccount());
        training.setAccount(account);

        // when
        Training savedTraining = trainingRepository.save(training);
        List<Training> foundTrainings = trainingRepository.findByAccount(savedTraining.getAccount());

        // then
        assertNotNull(savedTraining);
        assertFalse(foundTrainings.isEmpty());
        assertEquals(expectedFoundTraining, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);
        assertEquals(savedTraining.getDeviceName(), foundTraining.getDeviceName());
        assertEquals(savedTraining.getDeviceFirmware(), foundTraining.getDeviceFirmware());
        assertEquals(savedTraining.getType(), foundTraining.getType());
        assertEquals(savedTraining.getTime(), foundTraining.getTime());
        assertEquals(savedTraining.getCalories(), foundTraining.getCalories());
        assertEquals(savedTraining.getAccount(), foundTraining.getAccount());
        assertEquals(savedTraining.getCreatedDate(), foundTraining.getCreatedDate());
    }


}
