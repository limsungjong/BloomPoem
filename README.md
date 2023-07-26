# BloomPoem 프로젝트입니다.

<p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/ppt메인.PNG?raw=true"/></p>

인천일보 아카데미에서 진행한 팀 프로젝트입니다.

저희 ‘블룸 포엠’은 예약한 꽃을 픽업할 수 있는 ‘픽업’ 서비스와
그 외에도 꽃과 관련된 다양한 서비스들을 제공하고 있습니다.

## PPT
Youtube : https://www.youtube.com/watch?v=zAFSzJH9Br4&ab_channel=%EC%9D%B8%EC%B2%9C%EC%9D%BC%EB%B3%B4%EC%95%84%EC%B9%B4%EB%8D%B0%EB%AF%B8

# Description

- 개발 기간 : 2023.02.27 ~ 2023.03.31

- 참여 인원 : 3명

- 사용 기술

<p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/ppt도구.PNG?raw=true"/></p>

  - HTML, CSS, JavaScript, jQuery, Bootstrap
  - Java, Spring Boot 2.7.9,  Spring Data JPA, Thymeleaf, Gradle
  - Oracle 21c DataBase
  - GitHub, Visual Studio Code, IntelliJ
  - Kakao API (Pay, Map, Local)

# 프로젝트 일정 및 데이터베이스 ERD

<p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/ppt일정.PNG?raw=true"/></p>


<p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/pptERD.PNG?raw=true"/></p>

- 담당 구현 파트
  - 프로젝트 개발환경 구축, OracleDB로 데이터베이스 설계
  - 로그인 페이지, 회원가입 페이지 구현(우편주소API, 이메일 메일전송 API)
  - 메인 페이지 구현(Kakao Local API를 이용한 위치 검색 기능 구현)
  - 픽업 페이지 구현(Kakao MAP API를 이용한 지도 기능 구현)
  - 마이 페이지 구현(리뷰 작성, 수정, 삭제 기능 구현)

# Views

- 메인 페이지
  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/mainPageMove.gif?raw=true"/></p>
  
- 회원가입 페이지
  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/signUpSuccess2.gif?raw=true"/></p>

- 픽업 페이지

  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/pickUpMove.gif?raw=true"/></p>
  
  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/payAll.gif?raw=true"/></p>
  
- 쇼핑 페이지

  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/shopping1.gif?raw=true"/></p>
  
  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/Shopping2.gif?raw=true"/></p>
  
- 추천 페이지

  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/recomComple.gif?raw=true"/></p>
  
- 마이 페이지

  <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/myCom.gif?raw=true"/></p>
  
# Implementation

- #### 회원가입, 로그인 페이지
    1. Spring Boot Mail을 활용하여 유저의 Email로 OTP를 전송하여 OTP를 입력하여 로그인 되는 방식으로 구현
    <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/signUpSuccess2.gif?raw=true"/></p>

    <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/otp.png?raw=true"/></p>

- #### 픽업 페이지
    1. Kakao MAP API를 이용하여 지도 구현하고 비동기 통신으로 DB에 있는 꽃집 정보를 가져와서 마커로 지도에 표현, 드래그시에도 해당하는 위치의 꽃집 정보를 가져와 표시.
     
    <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/pcikCompl.gif?raw=true"/></p>
    
    2. Kakao LOCAL API를 이용하여 키워드 검색 기능 구현하여 해당하는 위치의 픽업 페이지 이동.
    
    <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/pickSearch.gif?raw=true"/></p>
    
    3. Kakao PAY API를 이용하여 결제 구현.
    
    <p align="center"><img src="https://github.com/limsungjong/BloomPoem/blob/master/이미지파일/pickBuyCom.gif?raw=true"/></p>
