package io.github.zunpiau.emitter.web.controller;

import io.github.zunpiau.emitter.LengthMatcher;
import io.github.zunpiau.emitter.SpringMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@PropertySource("classpath:application.properties")
public class EmitControllerTest extends SpringMvcTest {

    private static final String uid = "legalUid";
    private static final String url = "http://app.shadowland.cn/";
    private static final String text = "text";
    private static final String requestUrl = "/api/emit/";
    private static final RequestBuilder GET_REQUEST = MockMvcRequestBuilders.get(requestUrl + uid);
    @Autowired
    private EmitController controller;
    @Autowired
    private StringRedisTemplate template;
    @Value("${redis.key}")
    private String key;

    @Before
    public void cleanup() {
        template.delete(key + uid);
    }

    @Test
    public void testUrl() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        mockMvc.perform(GET_REQUEST)
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.BAD_REQUEST.value()));
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl + uid)
                .content(url.getBytes())
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
        mockMvc.perform(GET_REQUEST)
                .andExpect(MockMvcResultMatchers.redirectedUrl(url));
    }

    @Test
    public void testText() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        mockMvc.perform(GET_REQUEST)
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.BAD_REQUEST.value()));
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl + uid)
                .content(text.getBytes())
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
        mockMvc.perform(GET_REQUEST)
                .andExpect(MockMvcResultMatchers.content().string(text));
    }

    @Test
    public void testCutoff() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        char[] chars = new char[260];
        Arrays.fill(chars, '0');
        String tooLongUrl = url + new String(chars);
        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl + uid)
                .content(tooLongUrl.getBytes())
                .contentType(MediaType.TEXT_PLAIN_VALUE));
        mockMvc.perform(GET_REQUEST)
                .andExpect(MockMvcResultMatchers.header()
                        .string(HttpHeaders.LOCATION, new LengthMatcher(256)));
    }
}