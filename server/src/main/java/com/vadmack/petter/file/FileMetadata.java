package com.vadmack.petter.file;

import com.vadmack.petter.app.model.MongoModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@CompoundIndex(name = "path", def = "{'originalFilename' : 1, 'directory': 1}", unique = true)
@Document("fileMetadata")
public class FileMetadata extends MongoModel {
  @Indexed(unique = true)
  private String relativePath;
  private Long size;
  private String contentType;
  private String originalFilename;
  private Attachment attachment;
}
