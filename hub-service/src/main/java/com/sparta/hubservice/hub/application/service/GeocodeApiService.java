package com.sparta.hubservice.hub.application.service;


import com.sparta.commonmodule.exception.OperationNotAllowedException;
import com.sparta.hubservice.hub.application.dto.GeocodeResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j(topic = "GeocodeApiService : naver geocode api 호출")
public class GeocodeApiService {

    @Value("${naver.api.geocode.url}")
    private String geocodeUrl;

    @Value("${naver.api.geocode.client-id}")
    private String clientId;

    @Value("${naver.api.geocode.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, BigDecimal> getGeocodeAddress(String address) {
        // URL 설정
        String uri = UriComponentsBuilder.fromHttpUrl(geocodeUrl)
            .queryParam("query", address)
            .toUriString();

        // Http 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 및 응답 받기
        ResponseEntity<GeocodeResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, GeocodeResponse.class);

        if(response.getBody() != null && response.getBody().getAddress() != null) {
            log.error("해당 주소에 대한 위도, 경도 값을 찾을 수 없습니다. Address : {}", address);
            throw new OperationNotAllowedException();
        }

        // 위도, 경도 값 추출
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("latitude", new BigDecimal(response.getBody().getAddress().getY()));
        result.put("longitude", new BigDecimal(response.getBody().getAddress().getX()));

        return result;
    }

}
