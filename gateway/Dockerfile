FROM eclipse-temurin:17-jdk

WORKDIR /app

# Gradle 캐시 최적화 (빠른 빌드를 위해 종속성 미리 다운로드)
COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 전체 프로젝트 복사
COPY . .

# 개발 모드에서 Gateway 실행 (소스 코드 변경 즉시 반영)
ENTRYPOINT ["./gradlew", ":gateway:bootRun"]
