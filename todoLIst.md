## TODO
### Step1. 멀티 모듈 프로젝트 생성
- [X] 루트 모듈 설정
- [X] 공통 의존성 설정
    - [X] lombok
    - [X] test 관련 의존성

### Step2. 서브 모듈 생성 및 설정
#### 상품 모듈
- [ ] 상품 모듈 생성 및 설정
- [ ] 상품 구동을 위해 필요한 의존성 설정
- [ ] 상품 구동을 위한 환경 설정
- [ ] 상품 모듈 등록
- [ ] 상품 서버 통신 확인을 위한 API 구현
    - GET /api/v1/products/health-check

#### 주문 모듈
- [ ] 주문 모듈 생성 및 설정
- [ ] 주문 구동을 위해 필요한 의존성 설정
- [ ] 주문 서버 구동을 위한 환경 설정
- [ ] 주문 모듈 등록
- [ ] 주문 서버 통신 확인을 위한 API 구현
    - GET /api/v1/orders/health-check

### Step3. MSA 환경을 위한 API G/W, Service Discovery 구성
#### Eureka
- [X] 유레카 모듈 생성
- [X] 유레카 서버 서버 구동을 위한 의존성 설정
- [X] 유레카 서버 구동을 위한 환경 설정
- [X] 유레카 모듈 등록
- [X] 유레카 서버 동작을 위한 설정 추가 : @EnableEurekaServer
- [X] 유레카 서버 구동

#### Micro-service 등록
##### 상품 마이크로 서비스 등록
- [ ] 상품 마이크로 서비스 등록을 위한 의존성 추가 : eureka-client
- [ ] 상품 마이크로 서비스 등록을 위한 환경 설정 추가 : eureka-server endpoint
- [ ] 상품 마이크로 서비스 등록 여부 확인

##### 주문 마이크로 서비스 등록
- [ ] 주문 마이크로 서비스 등록을 위한 의존성 추가
- [ ] 주문 마이크로 서비스 등록을 위한 환경 설정 추가
- [ ] 주문 마이크로 서비스 등록 여부 확인

#### API Gateway
- [X] GW 모듈 생성
- [X] GW 서버 구동을 위한 의존성 설정
- [X] GW 서버 구동을 위한 환경 설정
- [X] GW 모듈 등록
- [X] GW 유레카 클라이언트 등록
- [X] GW 서버 구동

 ---
### Step4. 통신 확인
- [ ] GW -> 상품 마이크로서비스 통신 확인
- [ ] GW -> 주문 마이크로서비스 통신 확인