package co.com.alpha.bcb.mongo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoPropertiesClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean("mongoSecret")
    public MongoDBSecret dbSecretMongo(@Value("${spring.data.mongodb.uri}") String uri) {
        return MongoDBSecret.builder()
                .uri(uri)
                .build();
    }

    @Bean
    public ReactiveMongoClientFactory mongoProperties(@Qualifier("mongoSecret") MongoDBSecret secret) {
        MongoProperties properties = new MongoProperties();
        properties.setUri(secret.getUri());

        List<MongoClientSettingsBuilderCustomizer> list = new ArrayList<>();
        list.add(new MongoPropertiesClientSettingsBuilderCustomizer(properties));
        return new ReactiveMongoClientFactory(list);
    }
}
