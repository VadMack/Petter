package com.vadmack.petter.app.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.stereotype.Component;

@Component
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Value("${spring.data.mongodb.database}")
  private String dbName;

  @Value("${spring.data.mongodb.username}")
  private String username;

  @Value("${spring.data.mongodb.password}")
  private String password;

  @Value("${spring.data.mongodb.host}")
  private String host;

  @Value("${spring.data.mongodb.port}")
  private String port;

  @Value("${spring.data.mongodb.auto-index-creation}")
  private boolean autoIndexCreation;


  @Override
  public @NotNull MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(String.format("mongodb://%s:%s@%s:%s",
                    username, password, host, port));
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Override
  protected @NotNull String getDatabaseName() {
    return dbName;
  }

  @Override
  protected boolean autoIndexCreation() {
    return autoIndexCreation;
  }
}
