package com.sparta.shippingmanager.presentation;

import com.sparta.shippingmanager.application.service.ShippingManagerService;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping-managers")
@RequiredArgsConstructor
public class ShippingManagerController {

    private final ShippingManagerService shippingManagerService;

    @GetMapping("/assign")
    public ShippingManager assignManager(){
        return shippingManagerService.assign();
    }

}
