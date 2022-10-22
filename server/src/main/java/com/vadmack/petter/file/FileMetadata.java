package com.vadmack.petter.file;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Builder
@Data
@CompoundIndex(name = "path", def = "{'originalFilename' : 1, 'directory': 1}", unique = true)
@Document("fileMetadata")
public class FileMetadata {
  @MongoId
  private ObjectId id;
  @Indexed(unique = true)
  private String relativePath;
  private Long size;
  private String contentType;
  private String originalFilename;
  private Attachment attachment;
}
