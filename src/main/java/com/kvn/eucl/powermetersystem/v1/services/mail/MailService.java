package com.kvn.eucl.powermetersystem.v1.services.mail;

public interface MailService {
  void sendMail(String to, String message, String subject);
}
