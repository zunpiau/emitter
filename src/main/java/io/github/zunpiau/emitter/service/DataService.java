package io.github.zunpiau.emitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Service
@PropertySource("classpath:application.properties")
public class DataService {

    private final StringRedisTemplate template;
    @Value("${redis.key}")
    private String key;
    @Value("${redis.timeout}")
    private long timeout;

    @Autowired
    public DataService(StringRedisTemplate template) {
        this.template = template;
    }

    long getTimeout() {
        return timeout;
    }

    @Nullable
    public String getContent(String uid) {
        return template.opsForValue().get(genUrlKey(uid));
    }

    private String genUrlKey(String uid) {
        return key + uid;
    }

    public void saveContent(String uid, String text) {
        template.opsForValue().set(genUrlKey(uid), text, timeout, TimeUnit.SECONDS);
    }
}
