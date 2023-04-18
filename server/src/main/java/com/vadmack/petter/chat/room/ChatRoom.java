package com.vadmack.petter.chat.room;

import com.vadmack.petter.app.model.MongoModel;
import com.vadmack.petter.chat.message.ChatMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document("chatRooms")
public class ChatRoom extends MongoModel {
  @Indexed
  private String user1;
  @Indexed
  private String user2;
  private ChatMessage lastMessage;
  private String publicKey;

  /**
   * Saves chat participants ids in ascending order
   */
  public ChatRoom(String user1, String user2) {
    if (user1.compareTo(user2) < 1) {
      this.user1 = user1;
      this.user2 = user2;
    } else {
      this.user1 = user2;
      this.user2 = user1;
    }
  }

  public ChatRoom(String id, String user1, String user2, String publicKey) {
    setId(id);
    if (user1.compareTo(user2) < 1) {
      this.user1 = user1;
      this.user2 = user2;
    } else {
      this.user1 = user2;
      this.user2 = user1;
    }
    this.publicKey = publicKey;
  }

  public boolean isParticipant(String userId) {
    return this.user1.equals(userId) || this.user2.equals(userId);
  }

}
