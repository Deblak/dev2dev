package co.simplon.dev2dev_business.controllers;

import co.simplon.dev2dev_business.components.Notification;
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
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestBody String message) {
        notification.create(message);
    }

    @GetMapping("/test")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter(-1L);
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("SSE MVC - " + LocalTime.now().toString())
                            .id(String.valueOf(i))
                            .name("sse event - mvc");
                    emitter.send(event);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}
