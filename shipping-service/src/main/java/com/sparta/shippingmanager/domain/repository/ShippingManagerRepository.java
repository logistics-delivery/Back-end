package com.sparta.shippingmanager.domain.repository;
import com.sparta.shippingmanager.domain.model.ShippingManager;


import java.util.List;


public interface ShippingManagerRepository {

    // 가장 낮은 순서의 활성화된 담당자 찾기
    void updateAllManagerOrders(int max);

    List<ShippingManager> findNextManagerWithLock();
    ShippingManager save(ShippingManager shippingManager);

}
