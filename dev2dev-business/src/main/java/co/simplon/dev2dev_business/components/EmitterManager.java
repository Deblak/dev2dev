package co.simplon.dev2dev_business.components;

import co.simplon.dev2dev_business.configs.JwtHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;


import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class EmitterManager {

    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private final ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor();

    public SseEmitter subscribe()  {
        String userEmail = JwtHelper.getSubject();
        if (emitterMap.containsKey(userEmail)) {
            return emitterMap.get(userEmail);
        } else {
            var emitter = new SseEmitter(-1L);
            emitterMap.put(userEmail, emitter);
            emitter.onCompletion(() -> emitterMap.remove(userEmail));
            emitter.onTimeout(() -> {
                emitter.complete();
                emitterMap.remove(userEmail);
            });
            return emitter;
        }
    }

    @Async
    public void sendNotification(List<String> userList, String message) {
        userList.forEach( user -> {
            executors.execute( () -> {
                try {
                    SseEventBuilder event = SseEmitter.event()
                            .id(LocalTime.now().toString())
                            .data(message)
                            .comment("comment");
                    emitterMap.get(user).send(event);

                } catch (IOException e) {
                    emitterMap.get(user).completeWithError(e);
                    emitterMap.remove(user);
                }
            });
        });
    }
}
