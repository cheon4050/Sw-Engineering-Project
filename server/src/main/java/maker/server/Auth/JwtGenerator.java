package maker.server.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
@Component
public class JwtGenerator {

    String apikey = "abaakjahsdfjkhsoaiasudaasdaaaasdasdasdsdsdsfa";

    /**
     * 토큰생성
     * @param userIdx
     * @param ttlMills
     * @return
     */
    public String createJWT(int userIdx, long ttlMills) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        java.util.Map<String, Object> payloads = new HashMap<>();
        payloads.put("userIdx", userIdx);
        payloads.put("iat", now.getTime());
        //만료 시간 3시간
        payloads.put("exp", now.getTime() + ttlMills*1000);
        // 표준 클레임 셋팅
        JwtBuilder builder = Jwts.builder()
                .setClaims(payloads)
                .signWith(getSignKey());

        // 토큰 생성
        return builder.compact();
    }

    /**
     * 토큰 파싱
     * @param jwt
     * @return
     */
    public Jws<Claims> parseJwt(String jwt) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwt);

        return claims;
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(apikey.getBytes(StandardCharsets.UTF_8));
    }
}