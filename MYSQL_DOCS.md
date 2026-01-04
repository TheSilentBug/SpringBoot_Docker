# مستندات کامل MySQL

## مقدمه

MySQL یک سیستم مدیریت پایگاه داده رابطه‌ای (RDBMS) open-source است که توسط Oracle توسعه و نگهداری می‌شود. MySQL یکی از
محبوب‌ترین سیستم‌های دیتابیس در جهان است و به طور گسترده در برنامه‌های وب استفاده می‌شود.

## تاریخچه MySQL

### پیشینه

- **1995**: Michael Widenius و David Axmark MySQL را ایجاد کردند
- **2000**: MySQL AB تاسیس شد
- **2008**: Sun Microsystems MySQL را خرید
- **2010**: Oracle Sun Microsystems را خرید و MySQL را به دست آورد
- **2013**: MySQL 5.7 منتشر شد
- **2018**: MySQL 8.0 منتشر شد (نسخه استفاده شده در این پروژه)
- **2023**: MySQL 8.4 منتشر شد

### نسخه‌های مهم

- **MySQL 4.0**: پشتیبانی از InnoDB
- **MySQL 5.0**: Stored Procedures, Views, Triggers
- **MySQL 5.5**: بهبود عملکرد InnoDB
- **MySQL 5.7**: JSON Support, بهبود عملکرد
- **MySQL 8.0**: Window Functions, CTE, بهبود امنیت

## مفاهیم پایه MySQL

### دیتابیس (Database)

```sql
-- ایجاد دیتابیس
CREATE DATABASE hotel_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- استفاده از دیتابیس
USE hotel_db;

-- حذف دیتابیس
DROP DATABASE hotel_db;
```

### جدول (Table)

```sql
-- ایجاد جدول
CREATE TABLE hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- مشاهده ساختار جدول
DESCRIBE hotels;
SHOW CREATE TABLE hotels;

-- حذف جدول
DROP TABLE hotels;
```

### انواع داده (Data Types)

#### Numeric Types

```sql
TINYINT      -- -128 to 127
SMALLINT     -- -32768 to 32767
INT          -- -2147483648 to 2147483647
BIGINT       -- برای ID ها
DECIMAL(10,2) -- برای قیمت
FLOAT        -- اعداد اعشاری
DOUBLE       -- اعداد اعشاری با دقت بیشتر
```

#### String Types

```sql
CHAR(10)     -- طول ثابت
VARCHAR(255) -- طول متغیر
TEXT         -- متن طولانی
LONGTEXT     -- متن خیلی طولانی
```

#### Date/Time Types

```sql
DATE         -- فقط تاریخ (YYYY-MM-DD)
TIME         -- فقط زمان
DATETIME     -- تاریخ و زمان
TIMESTAMP    -- timestamp با timezone
YEAR         -- فقط سال
```

#### Boolean

```sql
BOOLEAN      -- معادل TINYINT(1)
```

### Constraints (محدودیت‌ها)

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT CHECK (age >= 18),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Indexes (ایندکس‌ها)

```sql
-- ایجاد ایندکس
CREATE INDEX idx_city ON hotels(city);
CREATE INDEX idx_name_city ON hotels(name, city);

-- ایندکس یکتا
CREATE UNIQUE INDEX idx_email ON users(email);

-- حذف ایندکس
DROP INDEX idx_city ON hotels;
```

## SQL در این پروژه

### موجودیت‌های اصلی

#### جدول Hotels

```sql
CREATE TABLE hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    address TEXT
);
```

#### جدول Rooms

```sql
CREATE TABLE rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hotel_id BIGINT NOT NULL,
    number VARCHAR(20) NOT NULL,
    type VARCHAR(50),
    capacity INT,
    price_per_night DECIMAL(10,2),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);
```

#### جدول Reservations

```sql
CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    guest_name VARCHAR(255) NOT NULL,
    guest_email VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);
```

### روابط (Relationships)

#### One-to-Many

```sql
-- یک هتل می‌تواند چندین اتاق داشته باشد
hotels (1) ----< rooms (many)
```

#### Many-to-One

```sql
-- چندین رزرو می‌توانند متعلق به یک اتاق باشند
reservations (many) ----< rooms (1)
```

### کوئری‌های متداول

#### SELECT

```sql
-- انتخاب تمام هتل‌ها
SELECT * FROM hotels;

-- انتخاب با شرط
SELECT * FROM hotels WHERE city = 'تهران';

-- انتخاب با JOIN
SELECT h.name, r.number, r.price_per_night
FROM hotels h
JOIN rooms r ON h.id = r.hotel_id
WHERE h.city = 'اصفهان';

-- انتخاب با Aggregate Functions
SELECT 
    h.name,
    COUNT(r.id) as room_count,
    AVG(r.price_per_night) as avg_price
FROM hotels h
LEFT JOIN rooms r ON h.id = r.hotel_id
GROUP BY h.id, h.name;
```

#### INSERT

```sql
-- افزودن هتل جدید
INSERT INTO hotels (name, city, address)
VALUES ('هتل عباسی', 'اصفهان', 'خیابان چهارباغ');

-- افزودن چند رکورد
INSERT INTO hotels (name, city) VALUES
('هتل اسپیناس', 'تهران'),
('هتل ترنج', 'کیش');
```

#### UPDATE

```sql
-- به‌روزرسانی
UPDATE hotels 
SET city = 'مشهد', address = 'خیابان امام رضا'
WHERE id = 1;

-- به‌روزرسانی با شرط
UPDATE rooms 
SET price_per_night = price_per_night * 1.1
WHERE hotel_id = 1;
```

#### DELETE

```sql
-- حذف
DELETE FROM hotels WHERE id = 1;

-- حذف با شرط
DELETE FROM reservations 
WHERE end_date < CURDATE();
```

### کوئری‌های پیشرفته

#### Subqueries

```sql
-- هتل‌هایی که اتاق دارند
SELECT * FROM hotels
WHERE id IN (
    SELECT DISTINCT hotel_id FROM rooms
);

-- هتل با بیشترین تعداد اتاق
SELECT * FROM hotels
WHERE id = (
    SELECT hotel_id 
    FROM rooms 
    GROUP BY hotel_id 
    ORDER BY COUNT(*) DESC 
    LIMIT 1
);
```

#### Window Functions (MySQL 8.0+)

```sql
-- رتبه‌بندی هتل‌ها بر اساس قیمت
SELECT 
    name,
    price_per_night,
    RANK() OVER (ORDER BY price_per_night DESC) as price_rank
FROM rooms;
```

#### Common Table Expressions (CTE) (MySQL 8.0+)

```sql
WITH hotel_stats AS (
    SELECT 
        hotel_id,
        COUNT(*) as room_count,
        AVG(price_per_night) as avg_price
    FROM rooms
    GROUP BY hotel_id
)
SELECT h.name, hs.room_count, hs.avg_price
FROM hotels h
JOIN hotel_stats hs ON h.id = hs.hotel_id;
```

### کوئری‌های استفاده شده در پروژه

#### پیدا کردن اتاق‌های موجود

```sql
SELECT r.*
FROM rooms r
WHERE r.hotel_id = ?
  AND r.id NOT IN (
      SELECT room_id 
      FROM reservations 
      WHERE (start_date <= ? AND end_date >= ?)
         OR (start_date <= ? AND end_date >= ?)
         OR (start_date >= ? AND end_date <= ?)
  );
```

این کوئری اتاق‌هایی را پیدا می‌کند که در بازه زمانی مشخص شده رزرو نشده‌اند.

## JPA/Hibernate در این پروژه

### Entity Mapping

```java
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String city;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
```

### Repository Queries

```java
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId " +
           "AND r.id NOT IN " +
           "(SELECT res.room.id FROM Reservation res " +
           "WHERE (res.startDate <= :to AND res.endDate >= :from))")
    List<Room> findAvailableRooms(
        @Param("hotelId") Long hotelId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
}
```

## تنظیمات MySQL در این پروژه

### Docker Compose Configuration

```yaml
db:
  image: mysql:8.4
  environment:
    MYSQL_ROOT_PASSWORD: password
    MYSQL_DATABASE: hotel_db
    MYSQL_USER: hotel
    MYSQL_PASSWORD: password
  command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

### Connection String

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### JPA Configuration

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

## بهترین روش‌ها (Best Practices)

### 1. استفاده از UTF8MB4

```sql
CREATE DATABASE hotel_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

برای پشتیبانی کامل از کاراکترهای فارسی و emoji.

### 2. استفاده از Foreign Keys

```sql
FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
```

برای حفظ یکپارچگی داده‌ها.

### 3. استفاده از Indexes

```sql
CREATE INDEX idx_hotel_city ON hotels(city);
CREATE INDEX idx_reservation_dates ON reservations(start_date, end_date);
```

برای بهبود عملکرد کوئری‌ها.

### 4. استفاده از Transactions

```java
@Transactional
public void createReservation(ReservationRequest request) {
    // عملیات دیتابیس
}
```

برای حفظ یکپارچگی داده‌ها.

### 5. استفاده از Prepared Statements

JPA/Hibernate به صورت خودکار از Prepared Statements استفاده می‌کند که از SQL Injection جلوگیری می‌کند.

### 6. Backup و Recovery

```bash
# Backup
mysqldump -u hotel -p hotel_db > backup.sql

# Restore
mysql -u hotel -p hotel_db < backup.sql
```

## مشکلات رایج و راه‌حل‌ها

### مشکل: خطای Encoding برای فارسی

**راه‌حل**:

```sql
-- استفاده از utf8mb4
CREATE DATABASE hotel_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### مشکل: خطای Connection Timeout

**راه‌حل**:

```yaml
spring:
  datasource:
    url: jdbc:mysql://host:port/db?serverTimezone=UTC&connectTimeout=60000
```

### مشکل: خطای Foreign Key Constraint

**راه‌حل**:

- بررسی وجود داده در جدول والد
- استفاده از ON DELETE CASCADE یا ON DELETE SET NULL

## امنیت MySQL

### 1. استفاده از کاربران محدود

```sql
CREATE USER 'hotel'@'%' IDENTIFIED BY 'strong_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON hotel_db.* TO 'hotel'@'%';
FLUSH PRIVILEGES;
```

### 2. استفاده از SSL

```yaml
url: jdbc:mysql://host:port/db?useSSL=true&requireSSL=true
```

### 3. رمزنگاری داده‌های حساس

استفاده از encryption برای رمز عبورها و اطلاعات حساس.

## بهینه‌سازی عملکرد

### 1. استفاده از Indexes

ایجاد ایندکس برای فیلدهای پرجستجو.

### 2. استفاده از EXPLAIN

```sql
EXPLAIN SELECT * FROM hotels WHERE city = 'تهران';
```

برای بررسی نحوه اجرای کوئری.

### 3. بهینه‌سازی JOIN ها

استفاده از INNER JOIN به جای OUTER JOIN در صورت امکان.

### 4. استفاده از Pagination

```java
Pageable pageable = PageRequest.of(page, size);
Page<Hotel> hotels = hotelRepository.findAll(pageable);
```

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی MySQL است.

---

**نکته**: MySQL یک سیستم دیتابیس قدرتمند و قابل اعتماد است. همیشه از آخرین نسخه استفاده کنید و به امنیت و بهینه‌سازی
توجه داشته باشید. در این پروژه از MySQL 8.4 استفاده شده است که آخرین نسخه است.

