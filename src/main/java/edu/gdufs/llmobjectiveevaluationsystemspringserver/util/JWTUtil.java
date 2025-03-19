package edu.gdufs.llmobjectiveevaluationsystemspringserver.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTUtil {

    private final String key;
    private final long durationHours;

    public JWTUtil(String key, long durationDays){
        this.key = key;
        this.durationHours = durationDays;
    }

    // 生成JWT令牌
    public String generateToken(String userId, String nickname, List<String> identity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("nickname", nickname);
        claims.put("identity", identity);
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * durationHours))
                .sign(Algorithm.HMAC256(key));
    }

    // 解密获取令牌里的信息
    public Map<String, Object> verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
