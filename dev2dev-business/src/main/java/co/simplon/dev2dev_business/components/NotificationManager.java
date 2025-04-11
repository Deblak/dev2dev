package co.simplon.dev2dev_business.components;

import co.simplon.dev2dev_business.entities.NotificationType;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import co.simplon.dev2dev_business.repositories.NotificationTypeRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationManager {

    private final EmitterManager emitterManager;
    private final NotificationTypeRepository notificationTypeRepository;
    private final AccountRepository repository;

    public NotificationManager(EmitterManager emitterManager, NotificationTypeRepository notificationTypeRepository, AccountRepository repository) {
        this.emitterManager = emitterManager;
        this.notificationTypeRepository = notificationTypeRepository;
        this.repository = repository;
    }

    @Async
    public void notifyUsersForArticle(String articleName) {
       NotificationType notificationType = notificationTypeRepository.findByTypeName("ARTICLE")
               .orElseThrow(() -> new IllegalArgumentException("ARTICLE not found !"));
       List<String> userNameList = repository.findAccountByNotificationType(notificationType.getId());
       emitterManager.sendNotification(userNameList, articleName);
    }
}
