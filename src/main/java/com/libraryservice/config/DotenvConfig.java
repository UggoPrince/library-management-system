package com.libraryservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

@Configuration
public class DotenvConfig {

    private final ConfigurableEnvironment environment;

    public DotenvConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        Properties properties = new Properties();
        dotenv.entries().forEach(entry -> properties.setProperty(entry.getKey(), entry.getValue()));
        environment.getPropertySources().addFirst(new PropertiesPropertySource("dotenvProperties", properties));
    }
}

