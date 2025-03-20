package com.sparta.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
public class JwtAuthenticationFilter implements WebFilter {
    private String secretKey;

    public JwtAuthenticationFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/api/v1/users/sign-up") || path.equals("/api/v1/users/sign-in")) {
            return chain.filter(exchange);  //회원가입, 로그인은 JWT 토큰인증 x
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.info(secretKey);
        log.info(request.getURI().toString());

        String token = extractToken(request); //토큰값을 Bearer 떼고 가져옴
        log.info(token);


        if (token == null || !validateToken(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        log.info(token);
        Claims claims = extractClaims(token); //사용자 정보 추출
        log.info(claims.getSubject());

        //응답 헤더에 사용자 정보 반환처리
        setAuthenticationHeader(claims,request);
        log.info(request.getHeaders().toString());
        return chain.filter(exchange);
    }



    private void setAuthenticationHeader(Claims claims, ServerHttpRequest request) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String user_id = claims.get("user_id", String.class);
        String role = claims.get("role", String.class);
        String slack_name = claims.get("slack_name",String.class);

        HttpHeaders headers = request.getHeaders();
        headers.add("user_id", user_id);
        headers.add("role", role);
        headers.add("slack_name", slack_name);
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


    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());

            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
            return true;
        } catch (Exception e) {
            log.error("Error extracting claims: " + e.getMessage());
            return false;
        }
    }


}
