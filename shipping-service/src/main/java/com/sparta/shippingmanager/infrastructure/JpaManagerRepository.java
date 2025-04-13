package com.sparta.shippingmanager.infrastructure;

import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.domain.repository.ShippingManagerRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaManagerRepository extends JpaRepository<ShippingManager, UUID>, ShippingManagerRepository {

    @Modifying
    @Query("UPDATE ShippingManager m SET m.shippingOrder = CASE" +
            " WHEN m.shippingOrder =:max THEN 1 ELSE m.shippingOrder + 1 END")
    void updateAllManagerOrders(@Param("max") int max);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM ShippingManager m WHERE m.isActive = true ORDER BY m.shippingOrder ASC")
    List<ShippingManager> findNextManagerWithLock();
}
