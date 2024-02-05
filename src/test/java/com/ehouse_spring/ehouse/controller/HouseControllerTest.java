package com.ehouse_spring.ehouse.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.hamcrest.Matchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class HouseControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void testAll() throws Exception {
        mvc.perform(get("/api/house"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[?(@.name == null)]").isEmpty());
    }

    // @Test
    // void testById() throws Exception {
    //     mvc.perform(get("/api/house/1"))
    //     .andExpect(status().isOk())
    //     .andExpect(jsonPath("$", hasKey("name")))
    //     .andExpect(jsonPath("$.id").value(1));
    // }
}
