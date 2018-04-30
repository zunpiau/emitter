package io.github.zunpiau.emitter.web.controller;

import io.github.zunpiau.emitter.common.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/uid")
public class UidController {

    private final UidGenerator generator;

    @Autowired
    public UidController(UidGenerator generator) {
        this.generator = generator;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getNumber() {
        return generator.generate();
    }

}
