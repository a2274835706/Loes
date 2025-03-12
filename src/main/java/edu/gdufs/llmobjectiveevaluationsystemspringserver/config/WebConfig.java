package edu.gdufs.llmobjectiveevaluationsystemspringserver.config;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    /**
     * 配置拦截器
     * <p>不拦截以下请求：</p>
     * <p>{@code /error}</p>
     * <p>{@code /user/login}</p>
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .excludePathPatterns(
                        "/error",
                        "/user/login");
    }
}
