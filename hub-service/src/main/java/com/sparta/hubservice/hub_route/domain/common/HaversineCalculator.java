package com.sparta.hubservice.hub_route.domain.common;


import com.sparta.hubservice.hub.domain.model.Hub;
import java.math.BigDecimal;

public class HaversineCalculator {

    private static final double R = 6371; // 지구 반지름 (km)

    public static double haversineDistance(Hub hub1, Hub hub2) {

        // 삼각함수 계산을 하기 위해 radian 단위로 변경
        double latRad1 = Math.toRadians(hub1.getLatitude().doubleValue());
        double latRad2 = Math.toRadians(hub2.getLatitude().doubleValue());
        double lonRad1 = Math.toRadians(hub1.getLongitude().doubleValue());
        double lonRad2 = Math.toRadians(hub2.getLongitude().doubleValue());

        double diffLat = latRad2 - latRad1;
        double diffLon = lonRad2 - lonRad1;

        // haversine 공식
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
            + Math.cos(latRad1) * Math.cos(latRad2) * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

}
