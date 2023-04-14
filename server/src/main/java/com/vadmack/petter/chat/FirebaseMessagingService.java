package com.vadmack.petter.chat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

  private final FirebaseMessaging firebaseMessaging;

  public void send(NotificationDto notificationDto) throws FirebaseMessagingException{
    Notification notification = Notification
            .builder()
            .setTitle(notificationDto.getSenderName())
            .setBody(notificationDto.getContent())
            .build();

    Message message = Message
            .builder()
            .setToken(notificationDto.getToken())
            .putAllData(notificationDto.getData())
            .setNotification(notification)
            .build();

    firebaseMessaging.send(message);
  }
}
