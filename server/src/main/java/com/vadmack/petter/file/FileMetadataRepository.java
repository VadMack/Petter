package com.vadmack.petter.file;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, ObjectId> {

  boolean existsByRelativePath(String relativePath);
}
