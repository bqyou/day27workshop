package tfip.nus.iss.Day27Workshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import static tfip.nus.iss.Day27Workshop.Constants.*;

@Configuration
public class AppConfig {

    @Value("${mongo.url}")
    private String mongoUrl;

    @Bean
    public MongoTemplate createOrder() {
        MongoClient c = MongoClients.create(mongoUrl);
        return new MongoTemplate(c, BGG);
    }

}