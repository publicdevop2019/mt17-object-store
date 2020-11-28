package com.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class MongoConfig {
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public TransactionTemplate getTransactionTemplate(MongoTemplate mongoTemplate, MongoTransactionManager mongoTransactionManager) {
        mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);
        return new TransactionTemplate(mongoTransactionManager);
    }
}
