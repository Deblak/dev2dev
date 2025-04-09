package co.simplon.dev2dev_business.components;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;


import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Notification {

    private final Set<SseEmitter> emitters = new CopyOnWriteArraySet<>();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public SseEmitter subscribe()  {
        var emitter = new SseEmitter(-1L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
//        emitter.onTimeout(() -> {
//            emitter.complete();
//            emitters.remove(emitter);
//        });
//        emitter.onError((e) -> emitters.remove(emitter));
        return emitter;
    }

    @Async
    public void create(String message) {
        SseEventBuilder event = SseEmitter.event().data(message);
        emitters.forEach( emitter ->
                executor.execute( () -> {
                    try {
                        emitter.send(event);
                    } catch (IOException e) {
//                        retry(emitter, event,0);
                        emitter.completeWithError(new RuntimeException(e));
                        emitters.remove(emitter);
//                        throw new RuntimeException(e);
                    }
                }));
    }

    void retry(SseEmitter emitter, SseEventBuilder event , int retryCount) {
        int count = retryCount;
        if (count < 3) {
            try {
                Thread.sleep(50L);
                emitter.send(event);
            } catch (IOException ignored) {
                retry(emitter, event, count + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            emitter.completeWithError(new RuntimeException("unable to connect with client"));
            emitters.remove(emitter);
        }
    }
}
