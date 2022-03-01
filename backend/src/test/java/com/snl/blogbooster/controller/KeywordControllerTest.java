package com.snl.blogbooster.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@AutoConfigureMockMvc
@SpringBootTest
public class KeywordControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 키워드검색결과가리턴된다() throws Exception {
        mvc.perform(get("/v1/data/keyword")
        .contentType(MediaType.APPLICATION_JSON).content("{\"keyword\":\"퀘스트 프로틴칩\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}