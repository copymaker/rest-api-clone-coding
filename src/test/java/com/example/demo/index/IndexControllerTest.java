package com.example.demo.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.common.BaseControllerTest;
import org.junit.jupiter.api.Test;

class IndexControllerTest extends BaseControllerTest {

    @Test
    void indexTest() throws Exception {
        mockMvc.perform(get("/api"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("_links.events").exists());
    }

}