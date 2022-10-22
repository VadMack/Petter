package com.vadmack.petter.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Attachment {
  private AttachmentType type;
  private String id;
}
