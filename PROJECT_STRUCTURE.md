# مستندات ساختار پروژه

این فایل شامل توضیحات کامل و جزئیات ساختار پروژه، موجودیت‌ها، روابط و جریان کار سیستم است.

## فهرست مطالب

- [ساختار کلی پروژه](#ساختار-کلی-پروژه)
- [ساختار Backend](#ساختار-backend)
- [ساختار Frontend](#ساختار-frontend)
- [موجودیت‌ها (Entities)](#موجودیت‌ها-entities)
- [روابط دیتابیس](#روابط-دیتابیس)
- [لایه‌های معماری](#لایه‌های-معماری)
- [جریان کار سیستم](#جریان-کار-سیستم)
- [فایل‌های پیکربندی](#فایل‌های-پیکربندی)

---

## ساختار کلی پروژه

```
SpringBoot_Docker/
├── backend/                    # بک‌اند Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/reyhan/hotel/
│   │   │   │       ├── HotelReservationApplication.java
│   │   │   │       ├── config/          # تنظیمات و پیکربندی
│   │   │   │       ├── controller/     # کنترلرهای REST API
│   │   │   │       ├── service/        # لایه سرویس (منطق کسب‌وکار)
│   │   │   │       ├── repository/     # لایه دسترسی به داده
│   │   │   │       ├── entity/         # موجودیت‌های JPA
│   │   │   │       └── dto/            # Data Transfer Objects
│   │   │   └── resources/
│   │   │       └── application.yml     # تنظیمات Spring Boot
│   │   └── test/                       # تست‌های واحد
│   ├── Dockerfile                       # تعریف image بک‌اند
│   └── pom.xml                         # وابستگی‌های Maven
├── frontend/                            # فرانت‌اند HTML/CSS/JS
│   ├── public/                         # فایل‌های استاتیک
│   │   ├── index.html                  # صفحه اصلی
│   │   ├── *.html                      # صفحات دیگر
│   │   ├── *.js                        # فایل‌های JavaScript
│   │   └── *.css                       # فایل‌های استایل
│   ├── Dockerfile                      # تعریف image فرانت‌اند
│   └── nginx.conf                      # تنظیمات Nginx
├── docker-compose.yml                  # ارکستریشن سرویس‌ها
├── README.md                           # مستندات اولیه
└── [مستندات فارسی]                     # فایل‌های مستندات فارسی
```

---

## ساختار Backend

### پکیج‌بندی (Package Structure)

```
com.reyhan.hotel
├── HotelReservationApplication.java    # کلاس اصلی Spring Boot
│
├── config/                              # پکیج تنظیمات
│   ├── DataLoader.java                 # بارگذاری داده‌های اولیه
│   ├── GlobalExceptionHandler.java     # مدیریت مرکزی خطاها
│   └── OpenApiConfig.java              # پیکربندی Swagger/OpenAPI
│
├── controller/                          # پکیج کنترلرها (REST API)
│   ├── HotelController.java            # API هتل‌ها
│   ├── ReservationController.java      # API رزرواسیون
│   ├── UserController.java             # API کاربران
│   ├── FlightController.java           # API پروازها
│   ├── TrainController.java            # API قطارها
│   └── BusController.java              # API اتوبوس‌ها
│
├── service/                             # پکیج سرویس‌ها (منطق کسب‌وکار)
│   ├── ReservationService.java         # منطق رزرواسیون
│   ├── UserService.java                # منطق کاربران
│   ├── FlightService.java              # منطق پروازها
│   ├── TrainService.java               # منطق قطارها
│   └── BusService.java                 # منطق اتوبوس‌ها
│
├── repository/                          # پکیج مخازن (دسترسی به داده)
│   ├── HotelRepository.java            # دسترسی به هتل‌ها
│   ├── RoomRepository.java              # دسترسی به اتاق‌ها
│   ├── ReservationRepository.java      # دسترسی به رزروها
│   ├── UserRepository.java             # دسترسی به کاربران
│   ├── FlightRepository.java           # دسترسی به پروازها
│   ├── FlightReservationRepository.java # دسترسی به رزرو پرواز
│   ├── TrainRepository.java            # دسترسی به قطارها
│   ├── TrainReservationRepository.java # دسترسی به رزرو قطار
│   ├── BusRepository.java               # دسترسی به اتوبوس‌ها
│   └── BusReservationRepository.java   # دسترسی به رزرو اتوبوس
│
├── entity/                              # پکیج موجودیت‌ها (JPA Entities)
│   ├── Hotel.java                      # موجودیت هتل
│   ├── Room.java                       # موجودیت اتاق
│   ├── Reservation.java                # موجودیت رزرو هتل
│   ├── User.java                       # موجودیت کاربر
│   ├── Flight.java                     # موجودیت پرواز
│   ├── FlightReservation.java          # موجودیت رزرو پرواز
│   ├── Train.java                      # موجودیت قطار
│   ├── TrainReservation.java           # موجودیت رزرو قطار
│   ├── Bus.java                        # موجودیت اتوبوس
│   └── BusReservation.java             # موجودیت رزرو اتوبوس
│
└── dto/                                 # پکیج DTO ها (Data Transfer Objects)
    ├── ReservationRequest.java          # DTO درخواست رزرو
    ├── UserRegistrationRequest.java     # DTO ثبت‌نام کاربر
    ├── FlightReservationRequest.java    # DTO رزرو پرواز
    ├── TrainReservationRequest.java     # DTO رزرو قطار
    └── BusReservationRequest.java       # DTO رزرو اتوبوس
```

### جزئیات کلاس‌های مهم

#### HotelReservationApplication.java

- **نقش**: کلاس اصلی Spring Boot
- **وظیفه**: راه‌اندازی اپلیکیشن و فعال‌سازی auto-configuration
- **Annotation**: `@SpringBootApplication`

#### DataLoader.java

- **نقش**: بارگذاری داده‌های اولیه
- **وظیفه**: ایجاد هتل‌ها و اتاق‌های نمونه هنگام راه‌اندازی
- **Interface**: `CommandLineRunner`

#### GlobalExceptionHandler.java

- **نقش**: مدیریت مرکزی خطاها
- **وظیفه**: تبدیل exceptionها به پاسخ‌های HTTP مناسب
- **Annotation**: `@ControllerAdvice`

---

## ساختار Frontend

### ساختار فایل‌ها

```
frontend/
├── public/
│   ├── index.html              # صفحه اصلی (رزرو هتل)
│   ├── flight.html             # صفحه رزرو پرواز
│   ├── train.html              # صفحه رزرو قطار
│   ├── bus.html                # صفحه رزرو اتوبوس
│   ├── regester.html           # صفحه ثبت‌نام
│   ├── config.html             # صفحه تنظیمات
│   │
│   ├── app.js                  # منطق اصلی رزرو هتل
│   ├── flight.js               # منطق رزرو پرواز
│   ├── train.js                # منطق رزرو قطار
│   ├── bus.js                  # منطق رزرو اتوبوس
│   ├── user.js                 # منطق کاربران
│   ├── config.js               # تنظیمات (آدرس بک‌اند)
│   │
│   ├── style.css               # استایل اصلی
│   └── styles.css              # استایل‌های اضافی
│
├── Dockerfile                  # تعریف image فرانت‌اند
└── nginx.conf                  # تنظیمات Nginx
```

### جزئیات فایل‌های مهم

#### index.html

- **نقش**: صفحه اصلی رزرو هتل
- **ویژگی‌ها**:
    - فرم جستجوی هتل
    - نمایش لیست هتل‌ها
    - نمایش اتاق‌های موجود
    - فرم رزرواسیون

#### app.js

- **نقش**: منطق اصلی رزرو هتل
- **توابع اصلی**:
    - `loadHotels()`: بارگذاری لیست هتل‌ها
    - `searchRooms()`: جستجوی اتاق‌های موجود
    - `renderRooms()`: نمایش اتاق‌ها
    - `reserve()`: ثبت رزرو

#### config.js

- **نقش**: تنظیمات اتصال به بک‌اند
- **محتوای پیش‌فرض**:
  ```javascript
  window.BACKEND_BASE_URL = 'http://localhost:8080';
  ```

---

## موجودیت‌ها (Entities)

### موجودیت‌های اصلی

#### 1. Hotel (هتل)

```java
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;        // نام هتل
    private String city;        // شهر
    private String address;     // آدرس
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;  // لیست اتاق‌ها
}
```

**ویژگی‌ها**:

- هر هتل می‌تواند چندین اتاق داشته باشد
- با حذف هتل، تمام اتاق‌های آن نیز حذف می‌شوند

#### 2. Room (اتاق)

```java
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String number;           // شماره اتاق
    private String type;             // نوع اتاق
    private int capacity;            // ظرفیت
    private double pricePerNight;    // قیمت هر شب
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;             // هتل مالک
}
```

**ویژگی‌ها**:

- هر اتاق متعلق به یک هتل است
- از Lazy Loading استفاده می‌کند

#### 3. Reservation (رزرو هتل)

```java
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;              // اتاق رزرو شده
    
    private String guestName;       // نام مهمان
    private String guestEmail;      // ایمیل مهمان
    private LocalDate startDate;    // تاریخ شروع
    private LocalDate endDate;      // تاریخ پایان
}
```

**ویژگی‌ها**:

- هر رزرو مربوط به یک اتاق است
- تاریخ‌ها به صورت LocalDate ذخیره می‌شوند

#### 4. User (کاربر)

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;   // نام
    private String lastName;    // نام خانوادگی
    private String username;     // نام کاربری (یکتا)
    private String email;        // ایمیل (یکتا)
    private String password;     // رمز عبور
}
```

#### 5. Flight (پرواز)

```java
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flightNumber;    // شماره پرواز
    private String origin;          // مبدا
    private String destination;      // مقصد
    private LocalDateTime departure; // زمان پرواز
    private int availableSeats;     // صندلی‌های موجود
    private double price;           // قیمت
}
```

#### 6. Train (قطار)

```java
@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String trainNumber;     // شماره قطار
    private String origin;          // مبدا
    private String destination;     // مقصد
    private LocalDateTime departure; // زمان حرکت
    private int availableSeats;     // صندلی‌های موجود
    private double price;           // قیمت
}
```

#### 7. Bus (اتوبوس)

```java
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String busNumber;       // شماره اتوبوس
    private String origin;          // مبدا
    private String destination;     // مقصد
    private LocalDateTime departure; // زمان حرکت
    private int availableSeats;     // صندلی‌های موجود
    private double price;           // قیمت
}
```

---

## روابط دیتابیس

### نمودار روابط (ERD)

```
┌──────────┐         ┌──────────┐         ┌──────────────┐
│  Hotel  │1      *  │  Room    │1      * │ Reservation  │
│─────────│─────────│──────────│─────────│──────────────│
│ id (PK) │         │ id (PK)  │         │ id (PK)      │
│ name    │         │ number   │         │ room_id (FK) │
│ city    │         │ type     │         │ guestName    │
│ address │         │ capacity │         │ guestEmail   │
└──────────┘         │ price    │         │ startDate    │
                     │ hotel_id │         │ endDate      │
                     │   (FK)   │         └──────────────┘
                     └──────────┘

┌──────────┐         ┌──────────────────┐
│  User   │         │ FlightReservation │
│─────────│         │──────────────────│
│ id (PK) │         │ id (PK)          │
│ ...     │         │ flight_id (FK)   │
└──────────┘         │ user_id (FK)     │
                     │ ...              │
┌──────────┐         └──────────────────┘
│ Flight  │1      *
│─────────│─────────
│ id (PK) │
│ ...     │
└──────────┘
```

### روابط اصلی

1. **Hotel ↔ Room**: One-to-Many
    - یک هتل می‌تواند چندین اتاق داشته باشد
    - هر اتاق متعلق به یک هتل است

2. **Room ↔ Reservation**: One-to-Many
    - یک اتاق می‌تواند چندین رزرو داشته باشد
    - هر رزرو مربوط به یک اتاق است

3. **Flight ↔ FlightReservation**: One-to-Many
    - یک پرواز می‌تواند چندین رزرو داشته باشد

4. **Train ↔ TrainReservation**: One-to-Many
    - یک قطار می‌تواند چندین رزرو داشته باشد

5. **Bus ↔ BusReservation**: One-to-Many
    - یک اتوبوس می‌تواند چندین رزرو داشته باشد

---

## لایه‌های معماری

### معماری سه‌لایه (Three-Tier Architecture)

```
┌─────────────────────────────────────────┐
│     Presentation Layer (Frontend)      │
│  ┌───────────────────────────────────┐ │
│  │  HTML/CSS/JavaScript              │ │
│  │  - index.html                     │ │
│  │  - app.js, flight.js, etc.        │ │
│  └───────────────────────────────────┘ │
└─────────────────────────────────────────┘
                    ↕ HTTP/REST
┌─────────────────────────────────────────┐
│     Business Logic Layer (Backend)     │
│  ┌───────────────────────────────────┐ │
│  │  Controllers (REST API)           │ │
│  │  ↓                                 │ │
│  │  Services (Business Logic)         │ │
│  │  ↓                                 │ │
│  │  Repositories (Data Access)       │ │
│  └───────────────────────────────────┘ │
└─────────────────────────────────────────┘
                    ↕ JDBC/JPA
┌─────────────────────────────────────────┐
│        Data Layer (Database)            │
│  ┌───────────────────────────────────┐ │
│  │  MySQL Database                    │ │
│  │  - hotels                          │ │
│  │  - rooms                           │ │
│  │  - reservations                    │ │
│  │  - users                           │ │
│  │  - flights, trains, buses         │ │
│  └───────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

### جریان داده

1. **Frontend → Backend**: درخواست HTTP (GET/POST)
2. **Controller**: دریافت درخواست و اعتبارسنجی
3. **Service**: اجرای منطق کسب‌وکار
4. **Repository**: دسترسی به دیتابیس
5. **Database**: ذخیره/بازیابی داده
6. **Response**: بازگشت داده به Frontend

---

## جریان کار سیستم

### جریان رزرو هتل

```
1. کاربر صفحه اصلی را باز می‌کند
   ↓
2. Frontend: loadHotels() → GET /api/hotels
   ↓
3. Backend: HotelController.listHotels()
   ↓
4. Repository: hotelRepository.findAll()
   ↓
5. Database: SELECT * FROM hotels
   ↓
6. Response: لیست هتل‌ها به Frontend
   ↓
7. Frontend: نمایش هتل‌ها در صفحه
   ↓
8. کاربر هتل و تاریخ‌ها را انتخاب می‌کند
   ↓
9. Frontend: searchRooms() → GET /api/hotels/{id}/rooms?available=true&from=...&to=...
   ↓
10. Backend: HotelController.listRooms()
    ↓
11. Repository: roomRepository.findAvailableRooms()
    ↓
12. Database: کوئری پیچیده برای پیدا کردن اتاق‌های موجود
    ↓
13. Response: لیست اتاق‌های موجود
    ↓
14. Frontend: نمایش اتاق‌ها
    ↓
15. کاربر یک اتاق را انتخاب می‌کند
    ↓
16. کاربر اطلاعات مهمان را وارد می‌کند
    ↓
17. Frontend: reserve() → POST /api/reservations
    ↓
18. Backend: ReservationController.createReservation()
    ↓
19. Service: ReservationService.createReservation()
    - بررسی اعتبار تاریخ‌ها
    - بررسی موجود بودن اتاق
    - ایجاد رزرو
    ↓
20. Repository: reservationRepository.save()
    ↓
21. Database: INSERT INTO reservations
    ↓
22. Response: رزرو ایجاد شده با ID
    ↓
23. Frontend: نمایش پیام موفقیت با شناسه رزرو
```

### جریان ثبت‌نام کاربر

```
1. کاربر روی "ثبت‌نام" کلیک می‌کند
   ↓
2. Frontend: نمایش فرم ثبت‌نام
   ↓
3. کاربر اطلاعات را وارد می‌کند
   ↓
4. Frontend: register() → POST /api/users/register
   ↓
5. Backend: UserController.register()
   ↓
6. Service: UserService.register()
   - بررسی یکتایی username و email
   - هش کردن رمز عبور
   - ایجاد کاربر
   ↓
7. Repository: userRepository.save()
   ↓
8. Database: INSERT INTO users
   ↓
9. Response: کاربر ایجاد شده
   ↓
10. Frontend: نمایش پیام موفقیت
```

---

## فایل‌های پیکربندی

### application.yml (Spring Boot)

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect

server:
  port: ${PORT:8080}

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### docker-compose.yml

```yaml
version: "3.9"
services:
  db:        # MySQL
  backend:   # Spring Boot
  frontend:  # Nginx
volumes:
  db_data:   # Volume برای دیتابیس
```

### nginx.conf

```nginx
server {
    listen 80;
    root /usr/share/nginx/html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
    
    location /api/ {
        proxy_pass http://backend:8080;
    }
}
```

---

## خلاصه

این پروژه یک سامانه کامل رزرواسیون است که شامل:

- **Backend**: Spring Boot با معماری لایه‌ای
- **Frontend**: HTML/CSS/JavaScript ساده
- **Database**: MySQL با روابط پیچیده
- **Infrastructure**: Docker و Docker Compose
- **موجودیت‌ها**: هتل، اتاق، رزرو، کاربر، پرواز، قطار، اتوبوس
- **API**: RESTful API با مستندات Swagger

برای اطلاعات بیشتر درباره هر بخش، به فایل‌های مستندات جداگانه مراجعه کنید.

---

**آخرین به‌روزرسانی**: این مستندات باید با تغییرات پروژه به‌روزرسانی شود.

