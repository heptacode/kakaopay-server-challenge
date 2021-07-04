package dev.hyunwoo.main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.hyunwoo.main.model.Memberships;
import dev.hyunwoo.main.repository.MembershipsRepository;

@WebMvcTest
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private MembershipsRepository membershipsRepository;

    @Test
    public void fetch() throws Exception {
        this.mockMvc.perform(get("/api/v1/membership/").header("X-USER-ID", "test1")).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void register() throws Exception {
        Memberships membership = new Memberships("shinsegae", "test2",
        "shinsegaepoint", "2021-06-20T14:48:30.011", "Y", 125000);

        this.mockMvc.perform(post("/api/v1/membership/").header("X-USER-ID", "test2")
                .content(objectMapper.writeValueAsString(membership))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void disable() throws Exception {
        this.mockMvc.perform(delete("/api/v1/membership/cj").header("X-USER-ID", "test1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(get("/api/v1/membership/spc").header("X-USER-ID", "test1")).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void accumulate() throws Exception {
        Memberships membership = new Memberships("shinsegae", "test2", "shinsegaepoint", "2021-06-20T14:48:30.011", "Y", 125000);
        
        this.mockMvc.perform(put("/api/v1/membership/point").header("X-USER-ID", "test1")
                .content(objectMapper.writeValueAsString(membership))).andExpect(status().isOk()).andDo(print());
    }
}