package com.vadmack.petter.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class NotificationDto {
  private String senderName;
  private String content;
  private String token;
  private Map<String, String> data;
}
