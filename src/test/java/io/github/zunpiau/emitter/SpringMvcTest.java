package io.github.zunpiau.emitter;

import io.github.zunpiau.emitter.web.WebConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration()
@ContextConfiguration(classes = WebConfig.class)
public abstract class SpringMvcTest extends SpringTest {

}
