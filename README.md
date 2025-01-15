## 요구사항 
1. 대기열 토큰
   - 대기열 토큰을 발급한다.
   - 대기열 토큰을 조회한다.
2. 예약
   - 예약 가능한 날짜와 해당 날짜의 좌석을 조회한다.
   - 예약 가능한 날짜 목록을 조회한다.
   - 날짜 정보를 입력받아 예약가능한 좌석을 조회한다.
3. 좌석 예약
   - 날짜와 좌석 정보를 입력받아 좌석을 예약한다.
   - 좌석을 예약하면 5분간 임시 배정되고 시간 내 결제가 완료되지 않으면 임시 배정은 해제된다.
4. 포인트
   - 포인트를 충전한다.
   - 포인트를 조회한다.
5. 결제
   - 결제 내역을 조회한다.
   - 결제가 완료되면 좌석을 배정하고 대기열 토큰을 만료시킨다.

---

## ERD
```mermaid
erDiagram
    Concert ||--o{ ConcertSchedule : related
    Reservation ||--|| Seat : related
    Reservation ||--|| ConcertSchedule : related
    Reservation ||--|| Payment : related
    Reservation }|--|| User : related
    Payment }|--|| User : related 
    UserPoint ||--|| User : related
    QueueToken ||--|| User : related
    
    Concert {
        string id PK
        string name
        string price
	      datetime created_at
	      datetime updated_at
    }
    ConcertSchedule {
        string id PK
        string concert_id
        datetime date
        string seat_id
   	    datetime created_at
	      datetime updated_at
    }
    Seat {
		    string id PK
		    int number
	      datetime created_at
	      datetime updated_at 
    }
    Reservation {
		    string id PK
		    string concert_schedule_id
		    string seat_id
		    string user_id  
		    string status
	      datetime created_at
	      datetime updated_at
    }
    Payment {
		    string id PK
		    string user_id 
		    string reservation_id
		    int total_price
    }
    User {
		    string id PK
		    string name 
		    string email 
		    string password
    }
    UserPoint {
		    string id PK
		    int point
		    string userId
			  datetime created_at
	      datetime updated_at
    }
    QueueToken {
		    string id PK
		    datetime expiration 
		    datetime created_at
	      datetime updated_at
    }
```
---

## 시퀀스 다이어그램

### 토큰 발급
```mermaid
sequenceDiagram
   autonumber
   actor User as 클라이언트
   
   participant QueueController
   participant QueueService 
   participant QueueRepository
   
   User ->> QueueController: POST /queue/token (token 발급 요청)
   QueueController ->> QueueService: 순서 정보가 담긴 token 생성 (status: ACTIVE , 만료시간: 10분)
   QueueService ->> QueueRepository: token 저장
   QueueRepository ->> QueueService: 저장된 token
   QueueService ->> QueueController: token 정보
   QueueController ->> User: 201 Created 응답
```

### 토큰 조회
```mermaid
sequenceDiagram
   autonumber
   actor User as 클라이언트
   participant QueueController
   participant QueueService
   participant QueueRepository
   loop 10초 주기 polling
       User ->> QueueController: GET /queue/status (token 조회)
       QueueController ->> QueueService: token 조회
       QueueService ->> QueueRepository: tokenId로 token 조회
       QueueRepository ->> QueueService: 조회된 token
       QueueService ->> QueueController: token 정보
       QueueController ->> User: 200 OK 응답
   end
   loop 10초 주기 scheduler
       QueueService ->> QueueRepository: 순서대로 10개씩 token의 status를 WAITING에서 ACTIVE로 변경 
   end
```

### 좌석 예약
```mermaid
sequenceDiagram
   autonumber
   actor User as 클라이언트
   participant ReservationController
   participant ReservationService
   participant QueueService
   participant ReservationRepository
   User ->> ReservationController: POST /reservation (좌석 예약)
   ReservationController ->> QueueService: ACTIVE하고 만료시간이 유효한 token인지 검증
   ReservationController ->> ReservationService: 예약 정보로 Reservation 생성 (status: PENDING)
   ReservationService ->> ReservationRepository: Reservation 저장
   ReservationRepository ->> ReservationService: 저장된 Reservation
   ReservationService ->> ReservationController: Reservation 정보
   ReservationController ->> User: 201 Created 응답
```

### 결제하기 
```mermaid
sequenceDiagram
   autonumber
   actor User as 클라이언트
   participant PaymentController
   participant PaymentService
   participant PaymentRepository
   participant QueueService
   participant ReservationService
   participant PointService
   User ->> PaymentController: POST /payment (결제하기)
   PaymentController ->> QueueService: ACTIVE하고 만료시간이 유효한 token인지 검증 
   PaymentController ->> ReservationService: Reservation 생성시간으로부터 5분이 지났는지 검증 
   PaymentController ->> PointService: point 잔액이 남았는지 검증
   PaymentController ->> PaymentService: point 차감 후 Payment 생성
   PaymentService ->> PaymentRepository: Payment 저장
   PaymentRepository ->> PaymentService: 저장된 Payment
   PaymentService ->> PaymentController: Payment 정보
   PaymentController ->> User: 201 Created 응답
```

## 스웨거 
### 대기열 생성 
![대기열생성](https://github.com/user-attachments/assets/8a7692e1-1c8c-49c9-b547-0b3299542faf)

### 대기열 조회 
![대기열조회](https://github.com/user-attachments/assets/ecdc5bde-4408-4f4a-ad17-037d243b569f)

### 콘서트 예약 일정 조회
![콘서트예약일정조회](https://github.com/user-attachments/assets/dd40a027-e543-4e8a-aba9-7df3284c233b)

### 콘서트 좌석 조회
![콘서트좌석조회](https://github.com/user-attachments/assets/b0cbd893-0cc3-4f6d-82fd-2ed7c7996189)

### 예약하기
![예약하기](https://github.com/user-attachments/assets/03268f08-5d77-44e9-b7ee-2d4f9a3c34ab)