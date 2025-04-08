package co.simplon.dev2dev_business.controllers;

import co.simplon.dev2dev_business.components.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@CrossOrigin("*")
public class SSEController {

    private final Notification notification;

    public SSEController(Notification notification) {
        this.notification = notification;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe() {
        return notification.subscribe();
    }

    @Deprecated(since = "for test purposes", forRemoval = true)
    @GetMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestParam String message) {
        notification.create(message);
    }
}
