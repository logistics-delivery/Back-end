package com.sparta.gateway.config;

import com.sparta.gateway.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll() // 모든 요청 허용
 /*                       .pathMatchers("api/v1/users/**").permitAll()
                        .pathMatchers("/api/v1/companys/**").hasAnyRole("MASTER","COMPANY","SHIPPING")
                        .pathMatchers("/api/v1/shippings/**").hasAnyRole("MASTER","SHIPPING")
                        .pathMatchers("/api/v1/hubs/**").hasAnyRole("MASTER","HUB","SHIPPING")
                        .pathMatchers("/api/v1/products/**").hasAnyRole("MASTER","COMPANY","HUB")
                        .pathMatchers("/api/v1/orders/**").permitAll()
                        .pathMatchers("/api/v1/payments/**").hasAnyRole("MASTER","COMPANY","SHIPPING")
                        .pathMatchers("/api/v1/slacks/**").hasRole("COMPANY") // 권한 기반 접근
                        .anyExchange().authenticated()
                )*/
                )
                .addFilterBefore(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(secretKey);
    }


}
