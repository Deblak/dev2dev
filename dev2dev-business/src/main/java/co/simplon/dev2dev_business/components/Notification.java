package co.simplon.dev2dev_business.components;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;


import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.*;

@Component
public class Notification {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor();

    public SseEmitter subscribe()  {
        var emitter = new SseEmitter(-1L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(emitter);
        });

        return emitter;
    }

    @Async
    public void create(String message) {
        emitters.forEach(emitter -> {
            executors.execute( () -> {
                try {
                    SseEventBuilder event = SseEmitter.event()
                            .id(LocalTime.now().toString())
                            .data(message)
                            .comment("comment");
                    emitter.send(event);

                } catch (IOException e) {
                    emitter.completeWithError(e);
                    emitters.remove(emitter);
                }
            });
        });
    }

//    void retry(SseEmitter emitter, SseEventBuilder event , int retryCount) {
//        int count = retryCount;
//        if (count < 3) {
//            try {
//                Thread.sleep(50L);
//                emitter.send(event);
//            } catch (IOException ignored) {
//                retry(emitter, event, count + 1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            emitter.completeWithError(new RuntimeException("unable to connect with client"));
//            emitters.remove(emitter);
//        }
//    }
}
