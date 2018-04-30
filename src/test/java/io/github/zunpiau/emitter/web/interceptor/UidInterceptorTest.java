package io.github.zunpiau.emitter.web.interceptor;

import io.github.zunpiau.emitter.SpringMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class UidInterceptorTest extends SpringMvcTest {

    private static final String uid = "illegalUid";
    private static final String requestUrl = "/api/emit/";
    @Autowired
    private WebApplicationContext context;

    @Test
    public void preHandle() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(MockMvcRequestBuilders.get(requestUrl + uid))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}