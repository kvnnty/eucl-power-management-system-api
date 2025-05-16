package com.kvn.eucl.powermetersystem.v1.services.mail.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.notifications.Notification;
import com.kvn.eucl.powermetersystem.v1.exceptions.ResourceNotFoundException;
import com.kvn.eucl.powermetersystem.v1.repositories.meters.MeterRepository;
import com.kvn.eucl.powermetersystem.v1.services.mail.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
  private final JavaMailSender mailSender;
  // private final TemplateEngine templateEngine;

  private final MeterRepository meterRepository;

  @Async
  @Override
  public void sendMail(Notification notification) {

    Meter meter = meterRepository.findById(notification.getMeter().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Meter not found"));

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("notifications@eucl.com");
    mailMessage.setTo(meter.getOwner().getEmail());
    mailMessage.setSubject("Token expiry notification");
    mailMessage.setText(notification.getMessage());
    mailSender.send(mailMessage);
  }

}
