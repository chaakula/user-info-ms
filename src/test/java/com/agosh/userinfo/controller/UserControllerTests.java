package com.agosh.userinfo.controller;

import com.agosh.userinfo.model.User;
import com.agosh.userinfo.repository.UserRepository;
import com.agosh.userinfo.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void getUserByEmail() throws Exception {
        given(userService.getUserInfo("chandu83343@gmail.com"))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
                ;
        given(userRepository.findByEmail("chandu83343@gmail.com"))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        mockMvc.perform(get("/api/user/chandu83343@gmail.com"))
                .andExpect(status().isOk());
        verify(userService).getUserInfo("chandu83343@gmail.com");
    }

    @Test
    void getUserByEmailWhichDoesnotExists() throws Exception {
        given(userService.getUserInfo("chandu83343@gmail.com"))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        given(userRepository.findByEmail("chandu83343@gmail.com"))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        mockMvc.perform(get("/api/user/chandu83343@gmails.com"))
                .andExpect(status().is4xxClientError());
        verify(userService).getUserInfo("chandu83343@gmails.com");
    }

    @Test
    void dateOfBirthValidation() throws Exception {
        User user = User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build();
        given(userService.addUser(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        given(userRepository.save(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        MvcResult result = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.dateOfBirth").value("Date of birth cannot be null")).andReturn();
    }

    @Test
    void dateOfBirthValidationFutureDate() throws Exception {

        User user = User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").dateOfBirth(getDateAfterDays(2)).build();
        given(userService.addUser(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        given(userRepository.save(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        MvcResult result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.dateOfBirth").value("Date of birth should be in the past")).andReturn();
    }

    @Test
    void addUser() throws Exception {

        User user = User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").dateOfBirth(getDateAfterDays(-2)).build();
        given(userService.addUser(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        given(userRepository.save(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        MvcResult result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateUser() throws Exception {

        User user = User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").dateOfBirth(getDateAfterDays(-2)).build();
        given(userService.updateUser(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        given(userRepository.save(User.builder().firstName("Chandra Sekhar").lastName("A").email("chandu83343@gmail.com").build()))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        MvcResult result = mockMvc.perform(put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteByEmail() throws Exception {
        given(userService.deleteUser("chandu83343@gmail.com"))
                .willReturn(User.builder().firstName("Chandra Sekhar").id(1L).lastName("A").email("chandu83343@gmail.com").build())
        ;
        mockMvc.perform(delete("/api/user/chandu83343@gmail.com"))
                .andExpect(status().isOk());
        verify(userService).deleteUser("chandu83343@gmail.com");
    }

    public static Date getDateAfterDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days); // +days
        return cal.getTime();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
