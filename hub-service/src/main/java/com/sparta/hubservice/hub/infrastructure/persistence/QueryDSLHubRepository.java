package com.sparta.hubservice.hub.infrastructure.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hubservice.hub.domain.model.Hub;
import com.sparta.hubservice.hub.domain.model.QHub;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryDSLHubRepository{

    private final JPAQueryFactory queryFactory;

    public Optional<Page<Hub>> searchByKeyword(String name, String address, Pageable pageable) {
        QHub hub = QHub.hub;

        BooleanBuilder predicate = new BooleanBuilder();

        if(name != null  && !name.isEmpty()) {
            predicate.or(hub.name.containsIgnoreCase(name));
        }
        if(address != null  && !address.isEmpty()) {
            predicate.or(hub.address.containsIgnoreCase(address));
        }

        List<Hub> hubList = queryFactory
            .selectFrom(hub)
            .where(predicate
                .and(hub.isDeleted.eq(false)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 페이징 처리
        long total = queryFactory
            .selectFrom(hub)
            .where(predicate
                .and(hub.isDeleted.eq(false)))
            .fetchCount();

        return Optional.of(new PageImpl<>(hubList, pageable, total));
    }
}
