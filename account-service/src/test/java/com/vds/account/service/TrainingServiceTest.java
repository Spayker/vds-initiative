package com.vds.account.service;

import com.vds.account.util.factory.AccountFactory;
import com.vds.account.util.factory.TrainingFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TrainingServiceTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingRepository repository;

    @BeforeEach
    public void setup() { initMocks(this); }

    @AfterEach
    public void clearDb() { repository.deleteAll(); }

    private static Stream<Arguments> provideCommonTrainings() {
        return Stream.of(
            Arguments.of(TrainingFactory.createTraining("miBand3", "v1.0", "jogging", 12000, 3500, new Date(), AccountFactory.createDummyAccount())),
            Arguments.of(TrainingFactory.createTraining("miBand4", "v1.2", "cycling", 12000, 5500, new Date(), AccountFactory.createDummyAccount()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Saves training by given training data")
    public void shouldCreateAccount(Training training) {
        // given
        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);

        // then
        assertNotNull(savedTraining);
        assertEquals(training.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(training.getDeviceFirmware(), savedTraining.getDeviceFirmware());

        verify(repository, times(1)).saveAndFlush(training);
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by id")
    public void shouldFindTrainingById(Training training) {
        // given
        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findById(training.getId())).thenReturn(Optional.ofNullable(savedTraining));
        Training foundTraining = trainingService.findTrainingById(training.getId());

        // then
        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by createdDate")
    public void shouldFindTrainingByCreatedDate(Training training) {
        // given
        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByCreatedDate(training.getCreatedDate())).thenReturn(savedTraining);
        Training foundTraining = trainingService.findTrainingByCreatedDate(training.getCreatedDate());

        // then
        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by Account")
    public void shouldFindTrainingByAccount(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByAccount(training.getAccount())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByAccount(training.getAccount());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by callories")
    public void shouldFindTrainingByCallories(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByCalories(training.getCalories())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByCalories(training.getCalories());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by device name")
    public void shouldFindTrainingByDeviceName(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByDeviceName(training.getDeviceName())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByDeviceName(training.getDeviceName());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by device firmware")
    public void shouldFindTrainingByDeviceFirmware(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByDeviceFirmware(training.getDeviceFirmware())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByDeviceFirmware(training.getDeviceFirmware());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by time")
    public void shouldFindTrainingByTime(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByTime(training.getTime())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByTime(training.getTime());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by type")
    public void shouldFindTrainingByType(Training training) {
        // given
        final int expectedTrainingCount = 1;

        // when
        when(repository.saveAndFlush(training)).thenReturn(training);
        Training savedTraining = trainingService.create(training);
        when(repository.findByType(training.getType())).thenReturn(Collections.singletonList(savedTraining));
        List<Training> foundTrainings = trainingService.findTrainingsByType(training.getType());

        // then
        assertNotNull(foundTrainings);
        assertEquals(expectedTrainingCount, foundTrainings.size());
        Training foundTraining = foundTrainings.get(0);

        assertEquals(foundTraining.getId(), savedTraining.getId());
        assertEquals(foundTraining.getCreatedDate(), savedTraining.getCreatedDate());
        assertEquals(foundTraining.getDeviceName(), savedTraining.getDeviceName());
        assertEquals(foundTraining.getDeviceFirmware(), savedTraining.getDeviceFirmware());
        assertEquals(foundTraining.getAccount(), savedTraining.getAccount());
        assertEquals(foundTraining.getCalories(), savedTraining.getCalories());
        assertEquals(foundTraining.getTime(), savedTraining.getTime());
        assertEquals(foundTraining.getType(), savedTraining.getType());

    }

}
