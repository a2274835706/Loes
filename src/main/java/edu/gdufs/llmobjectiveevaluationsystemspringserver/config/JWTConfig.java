package edu.gdufs.llmobjectiveevaluationsystemspringserver.config;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.duration-hours}")
    private long durationHours;

    @Bean
    protected JWTUtil jwt() {
        return new JWTUtil(key, durationHours);
    }

}
