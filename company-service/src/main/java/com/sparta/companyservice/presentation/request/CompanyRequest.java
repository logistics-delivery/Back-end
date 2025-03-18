package com.sparta.companyservice.presentation.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CompanyRequest {
    private String name;
    private String type;
    private UUID hubId;
    private String address;
}

