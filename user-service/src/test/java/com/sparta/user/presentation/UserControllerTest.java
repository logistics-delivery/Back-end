package com.sparta.user.presentation;

import com.sparta.user.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private String jwtToken;

    @Test
    void signUp() throws Exception {
        mockMvc.perform(post("/api/v1/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"asdfg123\"," +
                                "\"password\":\"asdf3gA12@\"," +
                                "\"email\":\"asdafv@naver.com\"," +
                                "\"slackName\":\"as3dgggv\"" +
                                "}")
                )
                .andExpect(status().isCreated());
    }

    @Test
    void signIn() throws Exception {
        mockMvc.perform(post("/api/v1/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"asdfg123\"," +
                                "\"password\":\"asdf3gA12@\"" +
                                "}")
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("Authorization");
    }

    @Test
    void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user_id", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("asdfg123"))
                .andExpect(jsonPath("$.email").value("asdafv@naver.com"))
                .andExpect(jsonPath("$.slackName").value("as3dgggv"))
                .andExpect(jsonPath("$.role").value("ROLE_COMPANY"));
    }

    @Test
    void updateUser() throws Exception{
        mockMvc.perform(put("/api/v1/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user_id", 1)
                        .content("{" +
                                "\"username\":\"asdf225s3\"," +
                                "\"password\":\"asdhdgA12@\"" +
                                "}")
                )
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception{
        mockMvc.perform(delete("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user_id", 1)
                )
                .andExpect(status().isNoContent());
    }
}