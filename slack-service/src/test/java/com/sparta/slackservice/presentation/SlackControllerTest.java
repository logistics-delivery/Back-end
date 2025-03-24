package com.sparta.slackservice.presentation;

import com.sparta.slackservice.application.service.SlackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SlackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SlackService slackService;

    @Test
    void createSlack() throws Exception{
        mockMvc.perform(post("/api/v1/slacks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"message\":\"안녕하세요!\"," +
                                "\"receiverId\":3" +
                                "}")
                        .header("user_id", 2)
                        .header("slack_name","sampleName")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slackName").value("sampleName"))
                .andExpect(jsonPath("$.message").value("안녕하세요!"))
                .andExpect(jsonPath("$.receiverId").value(3));
    }

    @Test
    void sendSlack() throws Exception {
        mockMvc.perform(get("/api/v1/slacks/send/{id}", "3dce1425-958c-4c07-bbbb-785a2c9e81d4")
        ).andExpect(status().isOk())
                .andExpect(content().string("전송성공"));
    }

    @Test
    void getSlack() throws Exception{
        mockMvc.perform(get("/api/v1/slacks/{id}", "4acbaa09-473f-4737-a107-5b085a0dbb23")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.slackName").value("sampleName"))
        .andExpect(jsonPath("$.message").value("안녕하세요!"))
        .andExpect(jsonPath("$.receiverId").value(3));

    }

    @Test
    void searchSlack() throws Exception {
        mockMvc.perform(get("/api/v1/slacks/search")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"receiverId\":3"+
                                "}")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.slackName").value("sampleName"))
                .andExpect(jsonPath("$.message").value("안녕하세요!"))
                .andExpect(jsonPath("$.receiverId").value(3));
    }

    @Test
    void modifySlack() throws Exception {
        mockMvc.perform(put("/api/v1/slacks/modify/{id}", "4acbaa09-473f-4737-a107-5b085a0dbb23")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"message\":\"감사합니다!\"," +
                                "\"receiverId\":3" +
                                "}")
                        .header("user_id", 2)
                        .header("slack_name","sampleName")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slackName").value("sampleName"))
                .andExpect(jsonPath("$.message").value("감사합니다!"))
                .andExpect(jsonPath("$.receiverId").value(3));
    }

    @Test
    void deleteSlack() throws Exception {
        mockMvc.perform(delete("/api/v1/slacks/{id}", "4acbaa09-473f-4737-a107-5b085a0dbb23")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_id", 2)
        ).andExpect(status().isNoContent());
    }
}