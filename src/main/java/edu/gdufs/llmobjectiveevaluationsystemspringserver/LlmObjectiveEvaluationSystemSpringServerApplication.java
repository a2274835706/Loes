package edu.gdufs.llmobjectiveevaluationsystemspringserver;

import cn.hutool.core.lang.Snowflake;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LlmObjectiveEvaluationSystemSpringServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LlmObjectiveEvaluationSystemSpringServerApplication.class, args);
    }

}
