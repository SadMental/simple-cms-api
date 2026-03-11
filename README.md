# 2026 신입 Back-End 개발자 코딩 과제 - 간단한 CMS REST API

2026년도 신입 Back-End 개발자 코딩 과제 제출입니다.\
간단한 CMS(Contents Management System) REST API 를 구현하는 것이 목표입니다.

## Spec

- Java 25
- Spring Boot 4
- Spring Security
- JPA
- H2 (db)
- Lombok

## 데이터 모델

### Contents

| 컬럼명                | 이름  | 설명          | 데이터 타입                      | 비고 |
|--------------------|-----|-------------|-----------------------------|----|
| id                 | 아이디 | 고유 아이디      | bigint primary key not null |    |
| title              | 제목  | contents 제목 | varchar(100) not null       |    |
| description        | 내용  | contents 내용 | text                        |    |
| view_count         | 조회수 | 조회수         | bigint not null             |    |
| created_date       | 생성일 | 생성한 날짜      | timestamp                   |    |
| created_by         | 생성자 | 생성한 사용자     | varchar(50) not null        |    |
| last_modified_date | 수정일 | 마지막 수정일     | timestamp                   |    |
| last_modified_by   | 수정자 | 마지막 수정한 사용자 | varchar(50)                 |    |

### Members
| 컬럼명                | 이름   | 설명                  | 데이터 타입                            | 비고 |
|--------------------|------|---------------------|-----------------------------------|----|
| id                 | 아이디  | 고유 아이디              | bigint primary key not null       |    |
| name               | 이름   | 사용자 이름              | varchar(50) not null unique       |    |
| password        | 비밀번호 | 암호화 비밀번호            | varchar(100) not null             |    |
| role         | 권한   | 사용자 권한(USER, ADMIN) | varchar(5) not null default 'USER' |    |
| created_date       | 생성일  | 생성한 날짜              | timestamp not null                |    |
| last_modified_date         | 수정일  | 마지막 수정일             | timestamp                         |    |


## 프로젝트 구조
```
com.malgn
├── configure          # 환경 설정 (Security, Swagger, JPA Auditing 등)
├── restcontroller     # REST API 엔드포인트
├── service            # 서비스 패키지
├── repository         # Spring Data JPA 리포지토리
├── entity             # JPA 엔티티 모델
├── dto / vo           # 데이터 전달 객체
├── error              # 공통 예외 처리
└── enums              # 공통 열거형 (Role 등)
```

## 실행 및 API 테스트 방법 (Swagger)

본 프로젝트는 SpringDoc OpenAPI를 활용하여 REST API 명세서를 자동화하였습니다.
프로젝트 루트 폴더에 첨부된 `api-docs.json`(또는 pdf) 파일을 참고하시거나, 서버 실행 후 아래 방법으로 직접 테스트하실 수 있습니다.

1. 서버 실행 시 InitDataConfiguration에서 테스트용 계정 자동 생성 (admin0~9, user0~9 / 비밀번호: 1234)
2. `http://localhost:8080/api` 접속하여 Swagger UI API 문서 진입
3. **[1. 인증 및 회원 API]** 의 `/login` 에서 위 계정으로 로그인하여 accessToken 발급
4. 우측 상단의 `Authorize` 버튼을 눌러 발급받은 토큰을 입력 후 전체 API 테스트 진행
  - admin 계정: 모든 게시글 수정/삭제 가능
  - user 계정: 본인 작성 글만 수정/삭제 가능

## 사용 AI 및 참고 자료

- Google Gemini (아키텍처 설계 보조 및 Swagger 문서화 가이드 활용)
- SpringDoc 공식 문서 및 깃허브 (https://springdoc.org/)










