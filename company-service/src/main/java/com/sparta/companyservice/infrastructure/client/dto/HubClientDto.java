package com.sparta.companyservice.infrastructure.client.dto;

import java.util.UUID;

public record HubClientDto(UUID hubId, String name, String address) {
}
