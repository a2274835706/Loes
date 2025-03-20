package edu.gdufs.llmobjectiveevaluationsystemspringserver.interceptor;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.IPUtil;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.RpsMonitor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final RpsMonitor rpsMonitor = new RpsMonitor();

    /**
     * <p>检验请求头是否携带 JWT 令牌</p>
     * @return {@code bool}
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        rpsMonitor.add(System.currentTimeMillis());
        int rps = rpsMonitor.getRps();
        String token = request.getHeader("Authorization");
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            if (ops.get(token) == null) {
                log.warn("{} RPS, {} {} from {} unauthorized", rps, request.getMethod(), request.getRequestURI(), IPUtil.getClientIP(request));
                throw new Exception(NormalResult.AUTHORIZED_ERROR);
            }
            log.info("{} RPS, {} {} from {} pass", rps, request.getMethod(), request.getRequestURI(), IPUtil.getClientIP(request));
            return true;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}