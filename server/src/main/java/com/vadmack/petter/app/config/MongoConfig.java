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
                           @Value("${spring.data.mongodb.password}") String password) {
    ConnectionString connectionString =
            new ConnectionString(String.format("mongodb://%s:%s@localhost:27017", username, password));
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)

            .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoClient mongoClient,
                                     @Value("${spring.data.mongodb.database}") String dbName) throws Exception {
    return new MongoTemplate(mongoClient, dbName);
  }
}
