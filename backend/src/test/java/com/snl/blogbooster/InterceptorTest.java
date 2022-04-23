package com.snl.blogbooster;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class InterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 인터셉터_예외_테스트() throws Exception {
        mockMvc.perform(get("/v1/data/influence")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());//200
    }

    @Test
    void 인터셉터_대상_테스트() throws Exception {
        mockMvc.perform(get("/v1/ping").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());//406
    }


}
