package io.github.zunpiau.emitter.service;

import io.github.zunpiau.emitter.SpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DataServiceTest extends SpringTest {

    @Autowired
    private DataService service;

    @Test
    public void saveUrl() throws InterruptedException {
        String url = "http://app.shadowland.cn";
        String uid = "uid";
        service.saveContent(uid, url);
        Assert.assertEquals(url, service.getContent(uid));
        Thread.sleep(service.getTimeout() * 1000 + 500);
        Assert.assertNull(service.getContent(uid));
    }
}