package com.vadmack.petter.app.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoConfig {

  @Bean
  public MongoClient mongo(@Value("${spring.data.mongodb.username}") String username,
                           @Value("${spring.data.mongodb.password}") String password,
                           @Value("${spring.data.mongodb.host}") String host,
                           @Value("${spring.data.mongodb.port}") String port

  ) {
    ConnectionString connectionString =
            new ConnectionString(String.format("mongodb://%s:%s@%s:%s", username, password, host, port));
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoClient mongoClient,
                                     @Value("${spring.data.mongodb.database}") String dbName) {
    return new MongoTemplate(mongoClient, dbName);
  }
}
