package com.cinema.config;

import com.cinema.data.entity.User;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

/**
 * MongoConfig.
 */
@Configuration
public class MongoConfig {

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        populate(mongoTemplate);
        return mongoTemplate;
    }

    private void populate(MongoTemplate mongoTemplate) {
        System.out.println("------ Populating -----");
        mongoTemplate.insert(
                new User("admin@cinema.com", "12345", "ADMIN", "admin adminovich"),
                "user"
        );

        Document doc = mongoTemplate.getCollection("user").find().first();
        System.out.println(doc.toJson());
        System.out.println("------ Populating Done-----");
    }

}
