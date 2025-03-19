package com.sparta.commonmodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Getter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true)
    private Long updatedBy;

    @Column(name = "is_deleted")
    @ColumnDefault("FALSE")
    private Boolean isDeleted;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", nullable = true)
    private Long deletedBy;

    // 생성을 위한 method
    public BaseEntity(Long userId) {
        this.createdBy = userId;
        this.updatedBy = userId;
        this.isDeleted = false;
    }

    // update를 위한 method
    public void update(Long userId) {
        this.updatedBy = userId;
    }

    // 소프트 delete를 위한 method
    public void delete(Long userId) {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userId;
    }
}
