package com.kvn.eucl.powermetersystem.v1.schedules;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kvn.eucl.powermetersystem.v1.entities.notifications.Notification;
import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.enums.tokens.ETokenStatus;
import com.kvn.eucl.powermetersystem.v1.repositories.notification.NotificationRepository;
import com.kvn.eucl.powermetersystem.v1.repositories.tokens.TokenRepository;
import com.kvn.eucl.powermetersystem.v1.services.mail.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenExpiryScheduler {

    private final TokenRepository tokenRepository;
    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    // Run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void expireTokens() {
        List<PurchasedToken> newTokens = tokenRepository.findByTokenStatus(ETokenStatus.NEW);
        List<PurchasedToken> expiredTokens = new ArrayList<>();

        for (PurchasedToken token : newTokens) {
            if (token.isExpired()) {
                token.setTokenStatus(ETokenStatus.EXPIRED);
                expiredTokens.add(token);
            }
        }

        if (!expiredTokens.isEmpty()) {
            tokenRepository.saveAll(expiredTokens);
            log.info("Marked {} tokens as expired", expiredTokens.size());
        }
    }

    // Check tokens expiring in next 5 hours and notify
    @Scheduled(cron = "0 0 * * * *")
    public void checkExpiringTokens() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusHours(5);

        List<PurchasedToken> tokens = tokenRepository.findTokensExpiringInWindow(now, limit);

        for (PurchasedToken token : tokens) {
            try {
                User user = token.getMeter().getOwner();
                if (user == null)
                    continue;

                String msg = String.format(
                        "Dear %s, REG is pleased to remind you that the token in meter %s is going to expire in 5 hours. Please purchase a new token.",
                        user.getNames(), token.getMeter().getMeterNumber());

                Notification notif = Notification.builder()
                        .meter(token.getMeter())
                        .message(msg)
                        .issuedDate(LocalDateTime.now())
                        .build();

                Notification saved = notificationRepository.save(notif);
                mailService.sendMail(saved);
            } catch (Exception ex) {
                log.error("Failed to process token notification: {}", ex.getMessage(), ex);
            }
        }

    }
}
