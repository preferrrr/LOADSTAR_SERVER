<div align="center" >

# ⭐LoadStar⭐
![image](https://github.com/YOON-CC/loadstar_v2/assets/87313979/74c5649d-c779-44aa-81d6-d668a53df03d)
</div>


<div align="center" >
</br>
LoadStar는 개발자가 꿈인 사람들이 자신의 진로 방향을 찾기위해 여러 사람들의 공부 그래프를 보며, 소통하고 참고하며, 어떤 이에게는 자신의 길을 보여주며 같은 방향을 걸어가고, 또 다른 이에게는 그 길을 바탕으로 새로운 지도를 만들어가는 웹 사이트 입니다.

</br>
</br>


[로드스타 바로가기](https://loadstar.site)

`2023/05/26 ~ 2023/07/21`
</div>

</br>

## UI

<div align="center" >

| 로그인, 회원가입 기능들 | 필터링 기능 |
|:--------------:|:---------------:|
| ![로그인](https://github.com/YOON-CC/loadstar_v2/assets/87313979/857be4b6-385a-40fa-996e-06511223b591) | ![필터기능](https://github.com/YOON-CC/loadstar_v2/assets/87313979/6cdd2693-84df-4251-a54a-c8b004aa4fbe) |

| 게시판 기능 | 그래프 그리기 기능|
|:---------------:|:---------------:|
| ![게시판 기능](https://github.com/YOON-CC/loadstar_v2/assets/87313979/f83aa3a0-b689-4c4b-90a7-b1a3687ea8e2) | ![그래프그리기 기능](https://github.com/YOON-CC/loadstar_v2/assets/87313979/3ac19545-ebe4-42f8-8d83-d368dfe549ac) |

</div>

</br>


## Skills
- Springboot 3.1.0
- Spring Data JPA
- Spring Security
- MySql
- nginx
- redis
- QueryDsl
- AWS EC2, RDS
</br>

## Goal
- 게시글은 제목, 내용 등과 함께 작성자의 커리어를 간트 차트로 보여줍니다.
- 회원가입 할 때 이메일로 인증합니다.
- 로그인한 사람만 게시글의 내용을 볼 수 있습니다.
- 조회수 증가의 중복을 방지합니다.
- 제목, 내용 또는 해시태그로 게시글을 검색합니다.
- 모든 N+1 문제를 해결합니다.
- 캐시를 적용합니다.
- CI/CD를 적용합니다.
- AWS 배포 후 도메인과 https를 적용합니다.

</br>

## Challenge
- JavaMailSender를 사용하여 메일로 인증번호를 전송
  - 메일 전송 작업는 완료까지 2~3초 처리 시간이 필요
  - 비동기 처리하여 응답 속도 개선
  - https://prefercoding.tistory.com/43
- 인증/인가를 위해 세션 방식을 사용하며, Redis에 세션 정보를 저장
- 조회수 증가의 중복 방지를 위해 세션 정보와 함께, 로그인한 동안 조회한 게시글의 인덱스를 같이 저장
  - 조회하지 않은 게시글만 조회수 증가
- 게시글의 제목 또는 내용으로 검색하기 위해 Mysql의 정규표현식(regexp) 사용
  - DB를 full scan하므로 성능상 문제가 생길 것
    - elastic search 사용 고려
- 해시태그로 게시글 검색
  - 게시글과 해시태그는 1:N으로 저장됨
  - 이때 해시태그 엔티티의 auto_increment 인덱스가 꼭 필요할까? 고민
  - 결과적으로 기존 대비 성능 최대 20프로 증가
  - https://prefercoding.tistory.com/40
- N+1 문제를 해결하기 위해 QueryDSL과 jpa batch를 사용
- github actions와 docker hub을 사용하여 CI/CD 구축
- 자주 조회되는 게시글들은 캐싱하여 응답 속도 개선과 DB 부하 감소
  - 세션 저장소와 캐시 저장소 분리
  - https://prefercoding.tistory.com/47
- 조회수 동시성 문제 해결
  - https://prefercoding.tistory.com/64
- 가비아에서 도메인 구입 후 AWS Route 53으로 도메인 적용
  - nginx와 certbot을 사용하여 https 적용

</br>

## API 명세서
* [LOADSTAR API 문서](https://docs.google.com/spreadsheets/d/1WdqjxqWwwNCfYBz0yHv42CSH6WzfixzCcjpVz03uOec/edit#gid=0)
![API 명세서](https://github.com/preferrrr/LOADSTAR_SERVER/assets/99793526/700fa76a-99a6-4bda-988f-3f56d091c183)

</br>

## ERD
![erd3](https://github.com/preferrrr/LOADSTAR_SERVER/assets/99793526/ecb58bd8-96f3-4fba-9e36-c3cbdf7581b4)
</br>



## 🔗Team
<div align="center" >


 
|조윤찬|이선호|강서연|
|:---:|:---:|:---:|
|<img width="230px" src="https://avatars.githubusercontent.com/u/87313979?v=4"/>|<img width="230px" src="https://avatars.githubusercontent.com/u/99793526?v=4" /> |<img width="230px" src="https://avatars.githubusercontent.com/u/101854418?v=4"/>|
|[@YOON-CC](https://github.com/YOON-CC)|[@preferrrr](https://github.com/preferrrr)|[@ddogong](https://github.com/ddogong)|
|Project Leader, Frontend Develop| Backend developer | Project Manager |

</div>

</br>
