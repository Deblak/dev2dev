package co.simplon.dev2dev_business.controllers;

import co.simplon.dev2dev_business.components.EmitterManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/sse")
@CrossOrigin("*")
public class SSEController {

    private final EmitterManager emitterManager;

    public SSEController(EmitterManager emitterManager) {
        this.emitterManager = emitterManager;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe() {
        return emitterManager.subscribe();
    }
}
