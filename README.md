Logistics-delivery
 ===

 # 프로젝트 소개

 ## 핵심기술 목표
 
### 1. 각 기능별 서비스 분리(MSA 기반 시스템 설계)
- 기능에 따른 개별 애플리케이션을 생성하여 서비스가 독립적으로 존재할 수 있도록 구현.
- 한 서비스에서 이슈가 발생하더라도 다른서비스에는 영향을 미치지않아 시스템의 가용성 향상.
- 서비스 추가에 대한 확장성을 고려한 설계.

### 2.서비스 간 데이터 공유 및 동기화
- 독립적인 서비스로 구현되어있지만, 서로 정보를 주고받을 수 있도록 서비스간 통신 설계.
- 트랜젝션 관리를 구현하여 하나라도 실패 시 전체롤백처리.
- 데이터 일관성을 유지하기위한 로직 구현.
  
### 3. 인증 및 보안
- 데이터를 보호하기위해 인증된 사용자만이 접근할 수 있도록 구현.
- 각 서비스 별 접근가능한 권한을 설정하여 보안을 강화.
- JWT(Json Web Token) 를 활용한 인증 및 인가체계를 구현하여 서비스의 보안을 유지

 ## 구현 목표
 
### 1. MSA 기반 아키텍처 & 커뮤니케이션
- 멀티모듈 프로젝트 구조로 각 기능을 독립적인 마이크로서비스로 분리하여 개발진행.
- 서비스 간 통신은 REST API를 통해 이루어지며, 다른 서브모듈 간의 통신은 FeignClient 사용.
- Spring Cloud Eureka를 이용한 서비스 디스커버리 및 Spring Cloud Gateway를 통한 API 라우팅.
  
### 2. 서비스 확장성 및 유연성
- 각 마이크로서비스는 수평 확장이 가능하며, 필요한 경우 독립적인 배포 및 유지보수.
- Layered Architecture로 클린 코드 유지 및 DDD를 통한 도메인 기반 설계.
 
### 3. 권한관리 및 보안
- JWT 인증: 사용자 인증 및 권한 관리를 위해 JWT를 사용하며, 각 요청에서 JWT 토큰을 검증하여 인증된 사용자만 접근하도록 처리.
- GateWay의 WebFluxSecurity: API Gateway에서 WebFluxSecurity를 이용해 JWT 토큰을 검증하고, 권한에 따라 요청에 대한 접근을 인가 처리.
- AOP를 통한 권한 인가: 서브모듈에서 세부 API별로 AOP를 활용해 권한 인가를 처리, 각 API에 맞는 권한 검사를 자동화
- 비밀번호 암호화: BCrypt 해시 알고리즘을 사용하여 비밀번호 입력 시 암호화 하여 저장.
- 데이터 유효성 검사: Spring Validator로 서버 측에서 유효성 검사 진행.

### 4. 사용자 경험 개선
- 슬랙 API 연동: 슬랙 API와 연동하여 메시지 작성 후 발송 시 실시간으로 전달되도록 구현.

### 5. API 문서화
- Swagger를 사용하여 API 문서 자동화 지원.

 # 개발 환경 소개
 ### 개발환경

 - spring boot 3.4.3
 - Gradle
 - java 17
 - Docker


 ### 기술스택
   | 분류            | 상세                                                       |
  |----------------|:---------------------------------------------------------|
  | Framework      | Spring Boot,JPA ,Spring Cloud (Eureka[MSA 간의 동적 서비스연결], Gateway[공통 진입점], Feign Client[서비스 통신]), AOP(사용자 권한 인가), QueryDSL (동적 쿼리 생성) |
  | Database       | PostgreSQL                                               |
  | Security       | Spring Security(인증,인가), JWT (토큰 기반 인증)            |
  | Documentation  | Swagger (API 문서화)                                       |
  |Test            | Spring Boot Test, JUnit                                   |
 # 프로젝트 실행 방법


 - Local 환경에서 필요한 .env 양식
 - Docker Compose 등 실행에 필요한 환경


 # 설계 산출물


 - [[도메인 다이어그램]](https://github.com/logistics-delivery/Back-end/wiki/%EB%8F%84%EB%A9%94%EC%9D%B8-%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8-&-%EB%8F%84%EB%A9%94%EC%9D%B8-%EB%8B%B4%EB%8B%B9%EC%9E%90-%EB%AA%A9%EB%A1%9D)
 - [[테이블 설계서]](https://github.com/logistics-delivery/Back-end/wiki/%ED%85%8C%EC%9D%B4%EB%B8%94-%EB%AA%85%EC%84%B8%EC%84%9C)
 - [[ERD]](https://github.com/logistics-delivery/Back-end/wiki/ERD-%EB%AA%85%EC%84%B8%EC%84%9C)
 - [[API 명세서]](https://github.com/logistics-delivery/Back-end/wiki/API-%EB%AA%85%EC%84%B8%EC%84%9C)
 - [[인프라 설계서]](https://github.com/logistics-delivery/Back-end/wiki/%EC%9D%B8%ED%94%84%EB%9D%BC-%EC%84%A4%EA%B3%84%EC%84%9C)
 - [[Conventions]] : 우리 조의 개발 규칙
     - [[Commit Message Conventions]]
     - [[Java Code Style]]
     - [[Git-flow]]
     - [[Package Structure]]


 # 개발 산출물


 - [[트러블 슈팅]] : 우리조의 기술적 고민과 원인 해결 과정
     - [[게이트웨이에서의 인가처리오류]](https://github.com/logistics-delivery/Back-end/wiki/%EA%B2%8C%EC%9D%B4%ED%8A%B8%EC%9B%A8%EC%9D%B4%EC%97%90%EC%84%9C%EC%9D%98-%EC%9D%B8%EA%B0%80%EC%B2%98%EB%A6%AC%28ReactiveSecurityContextHolder%29)
 - [[공통 관심 사항]]
     - [[AOP @Rolecheck 사용방법]](https://github.com/logistics-delivery/Back-end/wiki/%EA%B3%B5%ED%86%B5%EB%AA%A8%EB%93%88-AOP-@RoleCheck-%EC%82%AC%EC%9A%A9%EB%B0%A9%EB%B2%95)
     - [[AuditorAwareImpl 구현 및 @SQLRestriction 설정]](https://github.com/logistics-delivery/Back-end/wiki/AuditorAwareImpl-%EA%B5%AC%ED%98%84-%EB%B0%8F-@SQLRestriction-%EC%84%A4%EC%A0%95)
     - [[Git 시크릿 키 보호 방법]](https://github.com/logistics-delivery/Back-end/wiki/Git-%EC%8B%9C%ED%81%AC%EB%A6%BF-%ED%82%A4-%EB%B3%B4%ED%98%B8-%EB%B0%A9%EB%B2%95)
     - [[Docker 명령어]](https://github.com/logistics-delivery/Back-end/wiki/docker-%EB%AA%85%EB%A0%B9%EC%96%B4)
 - [[설계 대비 API 구현률]] : 이건 제가 드릴게요~!, 설계 API 수만 적어주세요
 - [[개발 측면에서 우리 조가 잘한 것들]]
     - 소통/협업
     - 자동화
     - 트러블슈팅


 # 시스템을 발전 시키기 위해 더 해본다면?


 - 현재 시스템의 문제점 인지 -> 개선을 위한 기술적 계획 및 접근


 # 협업 시 우리조가 잘한 것들

 # 협업 시 아쉽거나 부족했던 부분들


 # 팀원 소개 및 담당역할
| **역할**                |   **담당자**        |  **세부 업무**                     |
|-------------------------|:---------------------:|------------------------------------|
| **인증인가,<br> 사용자API** | 신다은<br>(팀장)      | - 회원가입,로그인 등의 사용자 정보CRUD 구현 <br> - 로그인 성공 시 JWT 토큰을 생성하여 사용자에게 전달되도록 함 <br> - JWT 토큰을 사용하여 회원정보를 인증, 내부payload값을 추출하여 서브모듈에서 사용가능하도록 구현 <br> - 인증된 정보를 바탕으로 사용자의 권한이 요청 url에 접근가능한지 gateway에서 우선적으로 인가처리를 할 수 있도록 구현 <br> - 세부 API 기능별로 AOP에서 인가처리를 추가적으로 진행하도록 구현|
|**슬랙API**|신다은<br>(팀장) | - 슬랙 메세지 관리 CRUD 구현<br> - 슬랙 외부 API를 연동하여 메세지 발송 시 실제 슬랙 사이트로 알림메세지가 전송되도록 함. <br> - Base Entity를 사용한 생성,수정,삭제 기록 저장 및 SoftDelete 구현<br> - QueryDSL을 이용한 슬랙 메세지 검색기능 구현|
|**허브API**|이소현<br>(테크리드) | - 허브 정보, 허브간 경로 CRUD 구현<br> - 허브 검색 기능 (Query DSL) 구현<br> - 최단 경로를 위한 허브간 경로 체크포인트 생성 및 조회 구현<br> -허브 내 배송품 입고 및 출고 처리 기능 구현 |
|**상품API**|서진영<br>(테크리드) | - 상품 CRUD 구현<br> - QueryDSL을 이용한 상품 검색 기능 구현<br> - 공통모듈에 swagger, Auditor Aware 구현 |
|**배송API**|권길남 | - 배송, 배송 로그, 배송 담당자 CRUD 구현<br> - 배송 담당자 배정 알고리즘 구현<br> - QueryDSL을 사용한 배송정보 검색기능 구현<br> - Docker 개발 환경세팅 |
|**업체API**|원지윤 | -  Company-service CRUD 개발 및 Spring Boot 기반 4계층 아키텍처 적용<br> - QueryDSL 기반 동적 검색 조건 및 페이징 기능 구현<br> - HTTP API 테스트, 도메인 및 서비스 계층 테스트 코드 작성|
|**주문API**|이용재 | - 주문 CRUD 구현<br> - 주문 생성 시 Product 서비스에 재고 차감 요청 기능 연동 (FeignClient 사용)<br> - 주문 생성 시 Shipping 서비스에 배송 생성 요청 기능 연동 (FeignClient 사용)<br> - Slack 도메인 연동을 위한 주문 + 배송 정보 응답 API 제공<br> - QueryDSL을 활용한 주문 검색 기능 구현 (주문명 + 상태 검색)|
