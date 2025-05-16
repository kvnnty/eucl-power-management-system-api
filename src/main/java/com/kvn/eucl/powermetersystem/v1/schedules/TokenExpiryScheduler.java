package com.kvn.eucl.powermetersystem.v1.schedules;

import java.time.Duration;
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

    // Run cron job every hour
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

    // Run cron job every hour
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

                Duration duration = Duration.between(now, token.getExpirationDate());
                long hours = duration.toHours();
                long minutes = duration.toMinutesPart();

                String timeRemaining;
                if (hours > 0) {
                    timeRemaining = String.format("%d hours and %d minutes", hours, minutes);
                } else {
                    timeRemaining = String.format("%d minutes", minutes);
                }
                String msg = String.format(
                        "Dear %s, REG is pleased to remind you that the token in meter %s will expire in %s. Please purchase a new token.",
                        user.getNames(),
                        token.getMeter().getMeterNumber(),
                        timeRemaining);

                Notification notif = Notification.builder()
                        .meter(token.getMeter())
                        .message(msg)
                        .issuedDate(now)
                        .build();

                Notification saved = notificationRepository.save(notif);
                mailService.sendMail(saved.getMeter().getOwner().getEmail(), saved.getMessage(),
                        "Token expiry notification");
            } catch (Exception ex) {
                log.error("Failed to process token notification: {}", ex.getMessage(), ex);
            }
        }
    }
}
