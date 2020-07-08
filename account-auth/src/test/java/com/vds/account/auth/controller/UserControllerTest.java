package com.vds.account.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vds.account.auth.domain.User;
import com.vds.account.auth.service.UserService;
import com.vds.account.auth.util.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private static Stream<Arguments> provideCommonUsers() {
        return Stream.of(
                Arguments.of(UserFactory.createUser("name1@gmail.com", "password", new Date())),
                Arguments.of(UserFactory.createUser("name2@gmail.com", "password", new Date()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonUsers")
    @DisplayName("Creates a new user and returns it")
    public void shouldRegisterNewUser(User user) throws Exception {
        String json = mapper.writeValueAsString(user);

        when(userService.create(any())).thenReturn(user);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

}
