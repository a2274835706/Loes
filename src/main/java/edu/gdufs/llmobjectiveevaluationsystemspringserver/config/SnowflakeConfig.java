package edu.gdufs.llmobjectiveevaluationsystemspringserver.config;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SnowflakeConfig {

    @Value("${snowflake.machine-bit}")
    private long MACHINE_BIT;

    @Value("${snowflake.data-center-bit}")
    private long DATA_CENTER_BIT;

    /**
     * 雪花算法ID生成器
     */
    @Bean
    protected PrefixSnowflake snowflake() {
        return new PrefixSnowflake(MACHINE_BIT, DATA_CENTER_BIT);
    }

}
