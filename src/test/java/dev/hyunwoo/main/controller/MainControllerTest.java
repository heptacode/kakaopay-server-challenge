package dev.hyunwoo.main.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        this.mockMvc.perform(get("/api/v1/membership/").header("X-USER-ID", "test1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void register() throws Exception {
        // Memberships membership1 = new Memberships("shinsegae", "test2", "shinsegaepoint", "2021-06-20T14:48:30.011", "Y", 125000);
        // Memberships membership2 = new Memberships("spc", "test2", "happypoint", "2021-06-20T14:48:29.831", "Y", 9000);
        // Memberships membership3 = new Memberships("cj", "test3", "cjone", "2021-06-22T14:41:34.011", "Y", 50000);
        // Memberships membership4 = new Memberships("spc", "test3", "happypoint", "2021-06-20T14:48:30.011", "Y", 35);
        // Memberships membership5 = new Memberships("cj", "test3", "cjone", "2021-06-20T14:48:30.011", "Y", 7777);

        // List<Memberships> memberships = Arrays.asList(membership1, membership2, membership3, membership4, membership5);
        
        given(membershipsRepository.register("shinsegae", "test2", "shinsegaepoint", 125000));

        this.mockMvc.perform(post("/api/v1/membership/").header("X-USER-ID", "test2")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void disable() throws Exception {
        this.mockMvc.perform(delete("/api/v1/membership/cj").header("X-USER-ID", "test1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(get("/api/v1/membership/spc").header("X-USER-ID", "test1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void accumulate() throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("membershipId", "cj");
        body.put("amount", 100);

        String content = objectMapper.writeValueAsString(body);

        this.mockMvc.perform(put("/api/v1/membership/point").contentType(MediaType.APPLICATION_JSON).header("X-USER-ID", "test1").content(content)).andExpect(status().isOk()).andDo(print());
    }
}