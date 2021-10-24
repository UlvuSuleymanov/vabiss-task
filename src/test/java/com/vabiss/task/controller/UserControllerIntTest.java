package com.vabiss.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;
import com.vabiss.task.model.response.UserResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String USERNAME = "testUsername";
    private String PASSWORD = "testPassword";

    @Test
    void signUp() throws Exception {
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.setUsername(USERNAME);
        signUpRequestModel.setPassword(PASSWORD);
        mockMvc.perform(
                post("/api/account/signup").content(objectMapper.writeValueAsString(signUpRequestModel))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        //dublicate username
        mockMvc.perform(
                post("/api/account/signup").content(objectMapper.writeValueAsString(signUpRequestModel))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity()).andReturn();


        // null password
        signUpRequestModel.setPassword("");
        mockMvc.perform(
                post("/api/account/signup").content(objectMapper.writeValueAsString(signUpRequestModel))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity()).andReturn();
    }

    @Test
    void login() throws Exception {
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.setUsername(USERNAME);
        signUpRequestModel.setPassword(PASSWORD);

        mockMvc.perform(
                post("/api/account/signup").content(objectMapper.writeValueAsString(signUpRequestModel))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();


        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(USERNAME);
        loginRequestModel.setPassword(PASSWORD);

        mockMvc.perform(
                post("/api/authorize").content(objectMapper.writeValueAsString(loginRequestModel))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString("accessToken")))
                .andReturn();

        loginRequestModel.setPassword(PASSWORD + "diff");
        mockMvc.perform(
                post("/api/authorize").content(objectMapper.writeValueAsString(loginRequestModel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();


    }

    @Test
    void showMe() throws Exception {
        mockMvc.perform(
                post("/api/account/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.setUsername(USERNAME);
        signUpRequestModel.setPassword(PASSWORD);

        mockMvc.perform(
                post("/api/account/signup").content(objectMapper.writeValueAsString(signUpRequestModel))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();


        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(USERNAME);
        loginRequestModel.setPassword(PASSWORD);

        MvcResult resultActions = mockMvc.perform(
                post("/api/authorize").content(objectMapper.writeValueAsString(loginRequestModel))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String loginResponseString = resultActions.getResponse().getContentAsString();
        LoginResponseModel loginResponseModel = objectMapper.readValue(loginResponseString, LoginResponseModel.class);

        System.out.println(loginResponseModel.getAccessToken());
        resultActions = mockMvc.perform(
                get("/api/account/me").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + loginResponseModel.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        UserResponseModel userResponseModel = objectMapper.readValue(resultActions.getResponse().getContentAsString(), UserResponseModel.class);

        assertEquals(USERNAME, userResponseModel.getUsername());

    }
}