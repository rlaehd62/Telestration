<div align='center'>

## Introduction
  
</div>

JavaFX를 통해서 구현한 유명 게임 `텔레스트레이션`, Netty와 Google Guava 그리고 JavaFX를 기반으로 제작한 멀티플레이 게임.
내 문서를 통해서 서버의 아이피와 포트 등을 설정하여 접속할 서버를 결정한다. 데이터를 SQLite로 관리하도록 만들었다.
로그인 후 닉네임을 클릭하면 새로고침하여 방의 목록을 다시 불러오도록 만들었다. 너무 옛날에 만든 게임이라 많이 아쉽다.

## 핵심 기능
- 회원가입/로그인
- 대기방 생성/삭제
- 텔레스트레이션 기능 (그림 그리기, 단어 맞추기)
- 실시간 채팅
> 정보를 입력하고 회원가입 버튼을 누르면 계정이 추가되고 로그인이 된다.

## Stack
> Java, JavaFX, SQLite, Netty, Google Guava

## Database ERD
![DB 설계](https://github.com/user-attachments/assets/d2a15e95-3940-4ad6-97bc-5a2c6ed7a95f)

## Login 설계
![Login 기능 설계 (클라이언트)](https://github.com/user-attachments/assets/4eaebc96-0220-483a-b769-30ef5103f063)

## 대기방 설계
![Waiting Room 기능 설계 (클라이언트)](https://github.com/user-attachments/assets/f25d67a6-0f8f-46fa-842d-935ead233f23)

## 게임 설계
![게임 시작 (설계도)](https://github.com/user-attachments/assets/bfb269ed-d51c-4f44-a774-bbfa0b0b82c7)
![게임 처리 (설계도)](https://github.com/user-attachments/assets/c532d022-84c2-4f10-adb2-874f0e881061)

## 서버 설계
![서버 SW](https://github.com/user-attachments/assets/f348b760-5fac-415a-9260-e5e8c583cc4a)

## 트러블슈팅

#### 그림 전송
초기 이 게임을 만들 때는 아무 것도 모르는 감자같은 상태였기에 어떻게 네트워크로 그림을 전송할지 매우 고민했다.
이 때 네트워크를 배우고 있었기 때문에, 어짜피 모든 존재는 바이트 단위로 변환되어 전송된다고 생각했고 관련된 방법을 찾기 시작했다.
이후 그림을 바이트 형식으로 변경한 뒤 객체에 담아서 직렬화하고 이를 전송하는 방식으로 네트워크 간의 그림이 전송되도록 문제를 해결하였다.
아마 네트워크를 통해 전송해야한다는 점에서 바이트 단위로 변경하고 이를 메타 데이터 등과 함께 전송할 방법을 찾다보니 해당 솔루션이 도출된 것으로 파악하고 있다.

#### 로직 처리
게임은 서로 다른 처리를 요구하는 많은 이벤트가 있었고, 난 이를 효율적으로 다룰 방법을 계속해서 찾고 있었다.
도중 Event-Driven 이라는 방법을 찾았고, 관련 라이브러리 중 Google Guava를 통해서 많은 로직을 유연한 구조로 설계하였다.
또한 네트워크로 주고받고 싶다는 마음에 직렬화를 선택했다, 그러나 지금 만든다면 JSON 등으로 만드는 방법도 있지 않을까 생각한다.

## In-Game

#### Pre-Game
![image](https://github.com/user-attachments/assets/c7500a18-1889-44b0-aa84-eef35f705408)
![image](https://github.com/user-attachments/assets/194e7f6c-37b6-4574-83b6-d1fb2927a36e)
![image](https://github.com/user-attachments/assets/fcef04a4-03d6-4b1c-bb2d-01142973f0cf)

### Game-Play
![image](https://github.com/user-attachments/assets/3f306606-fd0e-4c14-b720-a45ee08e19e4)
![image](https://github.com/user-attachments/assets/c6c28889-4255-4dda-a9b4-aade1da89a21)

