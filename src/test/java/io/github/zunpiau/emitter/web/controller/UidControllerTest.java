package io.github.zunpiau.emitter.web.controller;

import io.github.zunpiau.emitter.LengthMatcher;
import io.github.zunpiau.emitter.SpringMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UidControllerTest extends SpringMvcTest {

    @Autowired
    UidController controller;

    @Test
    public void getNumber() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/uid")
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(new LengthMatcher(8)));
    }

}