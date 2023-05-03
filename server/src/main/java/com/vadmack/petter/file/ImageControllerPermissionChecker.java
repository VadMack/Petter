package com.vadmack.petter.file;

import com.vadmack.petter.chat.room.ChatRoomService;
import com.vadmack.petter.file.metadata.Attachment;
import com.vadmack.petter.file.metadata.AttachmentType;
import com.vadmack.petter.file.metadata.FileMetadata;
import com.vadmack.petter.file.metadata.FileMetadataService;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

import static com.vadmack.petter.file.ImageService.USERS_PHOTO_STORAGE_FOLDER_NAME;

@RequiredArgsConstructor
@Component
public class ImageControllerPermissionChecker {

  private final FileMetadataService metadataService;
  private final ChatRoomService chatRoomService;

  public boolean isOwner(@NotNull User user, @NotNull String folderName) {
    return user.getId().equals(folderName);
  }

  public boolean hasDownloadPermission(@NotNull User user, @NotNull String folderName, @NotNull String fileName) {
    FileMetadata metadata = metadataService.getByRelativePath(
            Paths.get(USERS_PHOTO_STORAGE_FOLDER_NAME, folderName, fileName).toString());

    Attachment attachment = metadata.getAttachment();
    if (attachment == null || attachment.getType() != AttachmentType.CHAT_ROOM) {
      return true;
    } else {
      return chatRoomService.isParticipant(attachment.getId(), user.getId());
    }
  }
}
