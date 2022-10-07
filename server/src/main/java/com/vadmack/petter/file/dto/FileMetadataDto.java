package com.vadmack.petter.file.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class FileMetadataDto {
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String relativePath;
  private Long size;
  private String contentType;
  private String originalFilename;
}
