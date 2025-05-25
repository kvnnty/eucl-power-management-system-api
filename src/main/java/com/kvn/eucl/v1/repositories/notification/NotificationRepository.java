package com.kvn.eucl.v1.repositories.notification;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.v1.entities.notifications.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
