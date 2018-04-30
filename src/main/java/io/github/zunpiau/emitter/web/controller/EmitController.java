package io.github.zunpiau.emitter.web.controller;

import io.github.zunpiau.emitter.common.Strings;
import io.github.zunpiau.emitter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emit")
public class EmitController {

    private final DataService service;

    @Autowired
    public EmitController(DataService service) {
        this.service = service;
    }

    @PostMapping(value = "{uid}",
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity postUrl(@PathVariable String uid, @RequestBody String s) {
        service.saveContent(uid, Strings.cutoff(s));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{uid}")
    public ResponseEntity getUrl(@PathVariable String uid) {
        String url = service.getContent(uid);
        if (url == null)
            return ResponseEntity.badRequest().build();
        else {
            if (url.startsWith("http"))
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, url)
                        .build();
            else
                return ResponseEntity.ok(url);
        }
    }

}
