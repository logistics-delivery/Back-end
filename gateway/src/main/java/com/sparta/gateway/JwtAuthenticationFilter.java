package com.sparta.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter {
    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.debug("Request Path: {}", path);
        if (path.equals("/api/v1/users/sign-up") || path.equals("/api/v1/users/sign-in")) {
            return chain.filter(exchange);  //회원가입, 로그인은 JWT 토큰인증 x
        }

        String token = extractToken(exchange);

        if (token == null || !validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Claims claims = extractClaims(token); //Payload 부분 추출
        log.info("Extracted user_id from token: {}", claims.get("user_id"));
        log.info("Extracted role from token: {}", claims.get("role"));

        // 추출한 정보(header로 전달)
        ServerHttpRequest modifiedRequest =exchange.getRequest().mutate()
                .header("user_id",claims.get("user_id", String.class))
                .header("role", claims.get("role",String.class))
                .header("slack_id",claims.get("slack_id",String.class))
                .build();
        log.info(String.valueOf(exchange.getRequest().getHeaders()));

        // 수정된 요청을 포함하여 필터 체인으로 이동
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        //SecretKey 형태로 변환

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());

            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
