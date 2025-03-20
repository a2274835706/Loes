package edu.gdufs.llmobjectiveevaluationsystemspringserver.config;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.interceptor.RequestInterceptor;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.RpsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径的跨域请求
                .allowedOriginPatterns("*") // 允许的前端地址
                .allowedMethods("*") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的HTTP头
                .allowCredentials(true); // 是否允许发送Cookie
    }

}
