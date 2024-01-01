# 💬 취미 공유 SNS 서비스 'hobby-chain' 개발 프로젝트

## 🎯 프로젝트 목표

1. **객체 지향 원리**를 적용하여 **CleanCode**를 목표로 유지보수가 용이한 코드 설계
    - **SOLID 원리**의 이해를 바탕으로 최대한 5가지의 원칙을 준수하기 위해 노력하였습니다.
    - **추상체**를 제공하여 구현체가 변경되어도 다른 구현체에는 무리가 없도록 하였습니다.
  
      
2. 단순한 기능 구현이 아닌, 대용량 트래픽을 고려하여 **scale-out**을 고려한 설계
    - 동시 접속자 수가 많다고 가정하여, 많은 유저를 감당할 수 있는 **분산 시스템 아키텍처 구축**을 위해 노력하였습니다.
    - HTTP 특성의 이해를 기반으로 세션 및 이미지 스토리지를 사용하여 무상태를 보존하는 등 **확장에 용이**하도록 노력하였습니다.

3. **대용량 트래픽 처리**를 고려한 설계 및 구현
    - 세션 스토리지에 Redis를 사용하여 디스크 기반 데이터베이스보다 속도를 향상시켜 더 나은 성능을 가질 수 있도록 하였습니다.
    - 데이터베이스에 몰리는 트래픽을 분산시키기 위해 MySQL의 **Replication**을 통해 Master와 Slave 구조로 시스템을 구축하였습니다.
    - 옵티마이저의 쿼리 실행 계획을 예측해보고 인덱스 설계를 하여 쿼리를 실행하여 분석해보고 조회에 빠른 속도를 제공할 수 있도록 **쿼리튜닝을** 진행하였습니다.
    - 알림 서비스는 부가적인 기능이라 판단하고 비동기로 작동하는 메세지 큐를 통해 **알림 전송 관련 트래픽을 분산** 시켰습니다.
    - 속도 향상을 위해 이미지 업로드 시 CompletableFuture를 사용하여 **비동기**로 구현하였습니다.

## ⌨️ 사용 기술 및 개발 환경
**Language** : Java

**Framework** : Springboot

**Database / ORM** : MySQL, MyBatis

**Session Storage** : Redis

**IDE** : IntelliJ

**Development tools**: Gradle, Github, Slack


## ✏️ Architecture
![hobby-chain-architecture drawio (1)](https://github.com/f-lab-edu/hobby-chain/assets/125573226/4bad5dee-e2bc-47df-9c43-ad832cdf7eb3)


## 🗂️ ERD
인덱스에 대한 자세한 사항은 여기를 확인해 주세요 👉 [#20](https://github.com/f-lab-edu/hobby-chain/issues/20)

<img width="755" alt="hobby-chain-db" src="https://github.com/f-lab-edu/hobby-chain/assets/125573226/93ddce7b-744b-4d22-963f-6f91ab60fe72">



## 🔎 주요 기능
1. 회원가입 / 탈퇴
2. 로그인 / 로그아웃
3. 회원정보 수정
4. 게시글 작성 / 수정 / 삭제 / 조회
5. 피드 조회
6. 좋아요 기능
7. 댓글 작성 / 수정 / 삭제 / 조회
8. 팔로우 / 언팔로우


