package com.vds.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vds.account.util.factory.AccountFactory;
import com.vds.account.util.factory.TrainingFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TrainingControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private TrainingController trainingController;

    @Mock
    private TrainingService trainingService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    private static Stream<Arguments> provideCommonTrainings() {
        return Stream.of(
                Arguments.of(TrainingFactory.createTraining("miBand3", "v1.0", "jogging", 12000, 3500, new Date(), AccountFactory.createDummyAccount())),
                Arguments.of(TrainingFactory.createTraining("miBand4", "v1.2", "cycling", 12000, 5500, new Date(), AccountFactory.createDummyAccount()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonTrainings")
    @DisplayName("Looks for training by id")
    public void shouldGetTrainingById(Training training) throws Exception {
        when(trainingService.findTrainingById(training.getId())).thenReturn(training);

        mockMvc.perform(get("/trainings/" + training.getId()))
                .andExpect(jsonPath("$.id").value(training.getId()))
                .andExpect(jsonPath("$.createdDate").value(training.getCreatedDate()))
                .andExpect(jsonPath("$.deviceName").value(training.getDeviceName()))
                .andExpect(jsonPath("$.deviceFirmware").value(training.getDeviceFirmware()))
                .andExpect(jsonPath("$.calories").value(training.getCalories()))
                .andExpect(jsonPath("$.time").value(training.getTime()))
                .andExpect(jsonPath("$.type").value(training.getType()))
                .andExpect(status().isOk());
    }
    
    
    
}
