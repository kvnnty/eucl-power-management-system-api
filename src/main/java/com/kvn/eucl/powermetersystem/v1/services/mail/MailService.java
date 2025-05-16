package com.kvn.eucl.powermetersystem.v1.services.mail;

import com.kvn.eucl.powermetersystem.v1.entities.notifications.Notification;

public interface MailService {
  void sendMail(Notification notification);
}
