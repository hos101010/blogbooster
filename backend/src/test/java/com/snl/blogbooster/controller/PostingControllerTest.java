package com.snl.blogbooster.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PostingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 포스팅정보가리턴된다() throws Exception {
        mvc.perform(get("/v1/data/posting")
                .contentType(MediaType.APPLICATION_JSON).content("{\"url\":\"https://m.blog.naver.com/pinkjinjoo/222636391630\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
