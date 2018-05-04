package io.github.zunpiau.emitter.web.interceptor;

import io.github.zunpiau.emitter.SpringMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class UidInterceptorTest extends SpringMvcTest {

    private static final String illegalUid = "illegalUid";
    private static final String legalUid = "legalUid";
    private static final String requestUrl = "/api/emit/";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testBadUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(requestUrl + illegalUid))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testEmptyRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl + legalUid)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testPreHandle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl + legalUid)
                .contentType(MediaType.TEXT_PLAIN)
                .content("some text"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}