package com.sparta.slackservice.infastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.slackservice.application.dto.SlackRequestDto;
import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.application.dto.SlackSearchRequestDto;
import com.sparta.slackservice.domain.model.QSlack;
import com.sparta.slackservice.domain.model.Slack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SlackQueryDSLRepositoryImpl implements SlackQueryDSLRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<SlackResponseDto> searchSlack(SlackSearchRequestDto requestDto, Pageable pageable) {
        QSlack qSlack = QSlack.slack;  // Querydsl의 Q 클래스를 사용

        // 검색 조건을 작성할 BooleanExpression을 초기화
        BooleanExpression predicate = qSlack.isNotNull();  // 기본 조건 (모든 레코드를 가져오는 기본 조건)

        // 요청된 조건을 바탕으로 동적으로 조건을 추가
        if (requestDto.getSlackName() != null && !requestDto.getSlackName().isEmpty()) {
            predicate = predicate.and(qSlack.slackName.containsIgnoreCase(requestDto.getSlackName()));
        }

        if (requestDto.getMessage() != null && !requestDto.getMessage().isEmpty()) {
            predicate = predicate.and(qSlack.message.containsIgnoreCase(requestDto.getMessage()));
        }

        if (requestDto.getReceiverId() != null && !requestDto.getReceiverId().describeConstable().isEmpty()) {
            predicate = predicate.and(qSlack.receiverId.stringValue().containsIgnoreCase(String.valueOf(requestDto.getReceiverId())));
        }

        // JPAQueryFactory를 통해 쿼리 실행
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // 실제 쿼리 실행 (페이징 적용)
        List<Slack> result = queryFactory
                .selectFrom(qSlack)
                .where(predicate)
                .offset(pageable.getOffset())  // 페이지네이션 처리
                .limit(pageable.getPageSize())  // 페이지네이션 처리
                .fetch();

        // 전체 데이터 개수 가져오기 (페이징 처리를 위한 전체 개수)
        long totalCount = queryFactory
                .selectFrom(qSlack)
                .where(predicate)
                .fetchCount();

        // 결과를 Page 객체로 반환 (SlackResponseDto로 변환)
        List<SlackResponseDto> responseDtos = result.stream()
                .map(SlackResponseDto::new)
                .toList();

        return new PageImpl<>(responseDtos, pageable, totalCount);
    }
}
