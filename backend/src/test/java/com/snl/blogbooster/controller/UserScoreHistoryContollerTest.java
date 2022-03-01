package com.snl.blogbooster.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserScoreHistoryContollerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 사용자점수가리턴된다() throws Exception {
        mvc.perform(get("/v1/data/user/score")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":\"ckrzkssja123\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

