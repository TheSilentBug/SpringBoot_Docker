# مستندات کامل پروژه سامانه رزرواسیون هتل

## معرفی پروژه

این پروژه یک سامانه جامع رزرواسیون است که امکان رزرو هتل، پرواز، قطار و اتوبوس را فراهم می‌کند. این سیستم با استفاده از
معماری مدرن و تکنولوژی‌های روز دنیا طراحی و پیاده‌سازی شده است.

### ویژگی‌های اصلی

- **رزرواسیون هتل**: جستجو و رزرو اتاق‌های هتل با قابلیت فیلتر بر اساس تاریخ و وضعیت موجودی
- **رزرواسیون پرواز**: مدیریت و رزرو بلیط پرواز
- **رزرواسیون قطار**: رزرو بلیط قطار
- **رزرواسیون اتوبوس**: رزرو بلیط اتوبوس
- **مدیریت کاربران**: ثبت‌نام و مدیریت اطلاعات کاربران
- **رابط کاربری مدرن**: طراحی ریسپانسیو و کاربرپسند با HTML/CSS/JavaScript
- **API RESTful**: ارائه سرویس‌های کامل از طریق REST API
- **مستندات خودکار**: مستندات API با Swagger/OpenAPI
- **کانتینری‌سازی**: اجرای کامل پروژه با Docker و Docker Compose

## معماری سیستم

این پروژه از معماری سه‌لایه (Three-Tier Architecture) استفاده می‌کند:

### 1. لایه Presentation (نمایش)

- **Frontend**: صفحات HTML/CSS/JavaScript
- **سرور وب**: Nginx برای سرو فایل‌های استاتیک
- **پورت**: 8081

### 2. لایه Business Logic (منطق کسب‌وکار)

- **Backend**: Spring Boot Application
- **API**: RESTful Web Services
- **پورت**: 8080

### 3. لایه Data (داده)

- **دیتابیس**: MySQL 8.4
- **پورت**: 3306
- **ORM**: JPA/Hibernate

## تکنولوژی‌های استفاده شده

### Backend

- **Java 17**: زبان برنامه‌نویسی اصلی
- **Spring Boot 3.3.3**: فریم‌ورک اصلی بک‌اند
- **Spring Data JPA**: لایه دسترسی به داده
- **Hibernate**: ORM برای مدیریت موجودیت‌ها
- **MySQL Connector**: درایور اتصال به دیتابیس
- **Springdoc OpenAPI**: مستندات خودکار API
- **Maven**: مدیریت وابستگی‌ها و ساخت پروژه

### Frontend

- **HTML5**: ساختار صفحات
- **CSS3**: استایل‌دهی و طراحی
- **JavaScript (ES6+)**: منطق سمت کلاینت
- **Fetch API**: ارتباط با بک‌اند
- **Font Awesome**: آیکون‌ها

### Infrastructure

- **Docker**: کانتینری‌سازی اپلیکیشن
- **Docker Compose**: ارکستریشن سرویس‌ها
- **Nginx**: وب سرور برای فرانت‌اند
- **MySQL**: سیستم مدیریت دیتابیس

## ساختار پروژه

```
SpringBoot_Docker/
├── backend/                 # کدهای بک‌اند (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/reyhan/hotel/
│   │   │   │       ├── config/          # تنظیمات و پیکربندی
│   │   │   │       ├── controller/      # کنترلرهای REST API
│   │   │   │       ├── service/         # لایه سرویس (منطق کسب‌وکار)
│   │   │   │       ├── repository/      # لایه دسترسی به داده
│   │   │   │       ├── entity/          # موجودیت‌های دیتابیس
│   │   │   │       └── dto/             # Data Transfer Objects
│   │   │   └── resources/
│   │   │       └── application.yml      # تنظیمات Spring Boot
│   │   └── test/                        # تست‌های واحد
│   ├── Dockerfile                        # تعریف image بک‌اند
│   └── pom.xml                          # وابستگی‌های Maven
├── frontend/                             # کدهای فرانت‌اند
│   ├── public/                          # فایل‌های استاتیک
│   │   ├── index.html                   # صفحه اصلی
│   │   ├── *.html                       # صفحات دیگر
│   │   ├── *.js                         # فایل‌های JavaScript
│   │   └── *.css                        # فایل‌های استایل
│   ├── Dockerfile                        # تعریف image فرانت‌اند
│   └── nginx.conf                        # تنظیمات Nginx
├── docker-compose.yml                    # ارکستریشن سرویس‌ها
└── README.md                            # مستندات اولیه
```

## نحوه راه‌اندازی

### پیش‌نیازها

1. **Docker Desktop** (برای Windows/Mac) یا **Docker Engine** + **Docker Compose** (برای Linux)
2. دسترسی به اینترنت برای دانلود imageهای Docker

### راه‌اندازی با Docker Compose (پیشنهادی)

1. **کلون کردن یا دانلود پروژه**:
   ```bash
   git clone <repository-url>
   cd SpringBoot_Docker
   ```

2. **اجرای تمام سرویس‌ها**:
   ```bash
   docker compose up --build -d
   ```

   این دستور:
    - imageهای Docker را می‌سازد
    - کانتینرهای MySQL، Backend و Frontend را راه‌اندازی می‌کند
    - تمام وابستگی‌ها را به صورت خودکار مدیریت می‌کند

3. **بررسی وضعیت سرویس‌ها**:
   ```bash
   docker compose ps
   ```

4. **دسترسی به سرویس‌ها**:
    - **Frontend**: http://localhost:8081
    - **Backend API**: http://localhost:8080
    - **Swagger UI**: http://localhost:8080/swagger-ui.html
    - **API Docs (JSON)**: http://localhost:8080/v3/api-docs
    - **MySQL**: localhost:3306

5. **توقف سرویس‌ها**:
   ```bash
   docker compose down
   ```

6. **توقف و حذف داده‌ها**:
   ```bash
   docker compose down -v
   ```

### راه‌اندازی محلی (بدون Docker)

#### پیش‌نیازها

- JDK 17 یا بالاتر
- Maven 3.6+
- MySQL 8.0+
- یک وب سرور ساده (برای فرانت‌اند) یا استفاده از Live Server در VS Code

#### مراحل

1. **راه‌اندازی MySQL**:
   ```sql
   CREATE DATABASE hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'hotel'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON hotel_db.* TO 'hotel'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. **اجرای بک‌اند**:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. **باز کردن فرانت‌اند**:
    - فایل `frontend/public/index.html` را در مرورگر باز کنید
    - یا از یک Live Server استفاده کنید
    - یا با Python: `python -m http.server 8081` در پوشه `frontend/public`

4. **تغییر آدرس بک‌اند در فرانت‌اند**:
   فایل `frontend/public/config.js` را ویرایش کنید:
   ```javascript
   window.BACKEND_BASE_URL = 'http://localhost:8080';
   ```

## API های اصلی

### هتل‌ها

#### دریافت لیست هتل‌ها

```
GET /api/hotels
```

**پاسخ نمونه**:

```json
[
  {
    "id": 1,
    "name": "هتل عباسی",
    "city": "اصفهان",
    "address": "خیابان چهارباغ"
  }
]
```

#### دریافت اتاق‌های یک هتل

```
GET /api/hotels/{id}/rooms
```

**پارامترهای Query (اختیاری)**:

- `available=true`: فقط اتاق‌های موجود
- `from=YYYY-MM-DD`: تاریخ شروع
- `to=YYYY-MM-DD`: تاریخ پایان

**مثال**:

```
GET /api/hotels/1/rooms?available=true&from=2025-01-15&to=2025-01-20
```

### رزرواسیون

#### ثبت رزرو جدید

```
POST /api/reservations
```

**بدنه درخواست**:

```json
{
  "roomId": 1,
  "guestName": "علی احمدی",
  "guestEmail": "ali@example.com",
  "startDate": "2025-01-15",
  "endDate": "2025-01-20"
}
```

**پاسخ موفق**:

```json
{
  "id": 1,
  "guestName": "علی احمدی",
  "guestEmail": "ali@example.com",
  "startDate": "2025-01-15",
  "endDate": "2025-01-20",
  "room": {
    "id": 1,
    "number": "101",
    "type": "سوئیت",
    "capacity": 2,
    "pricePerNight": 500000
  }
}
```

#### دریافت اطلاعات یک رزرو

```
GET /api/reservations/{id}
```

### کاربران

#### ثبت‌نام کاربر جدید

```
POST /api/users/register
```

**بدنه درخواست**:

```json
{
  "firstName": "علی",
  "lastName": "احمدی",
  "username": "ali_ahmadi",
  "email": "ali@example.com",
  "password": "password123"
}
```

### پرواز، قطار و اتوبوس

APIهای مشابه برای رزرو پرواز، قطار و اتوبوس در کنترلرهای مربوطه موجود است:

- `/api/flights`
- `/api/trains`
- `/api/buses`

## جریان کار سیستم

### 1. جریان رزرو هتل

1. **کاربر صفحه اصلی را باز می‌کند** (`index.html`)
2. **سیستم لیست هتل‌ها را از API دریافت می‌کند** (`GET /api/hotels`)
3. **کاربر هتلی را انتخاب می‌کند و تاریخ‌های اقامت را وارد می‌کند**
4. **سیستم اتاق‌های موجود را جستجو می‌کند** (`GET /api/hotels/{id}/rooms?available=true&from=...&to=...`)
5. **کاربر یک اتاق را انتخاب می‌کند**
6. **کاربر اطلاعات مهمان را وارد می‌کند**
7. **سیستم رزرو را ثبت می‌کند** (`POST /api/reservations`)
8. **سیستم شناسه رزرو را به کاربر نمایش می‌دهد**

### 2. جریان ثبت‌نام کاربر

1. **کاربر روی دکمه "ثبت‌نام" کلیک می‌کند**
2. **فرم ثبت‌نام نمایش داده می‌شود**
3. **کاربر اطلاعات را وارد می‌کند**
4. **سیستم درخواست ثبت‌نام را به API ارسال می‌کند** (`POST /api/users/register`)
5. **سیستم پاسخ را به کاربر نمایش می‌دهد**

## پیکربندی‌ها

### متغیرهای محیطی (Docker Compose)

در فایل `docker-compose.yml`:

- **MySQL**:
    - `MYSQL_ROOT_PASSWORD`: رمز عبور root
    - `MYSQL_DATABASE`: نام دیتابیس
    - `MYSQL_USER`: نام کاربری
    - `MYSQL_PASSWORD`: رمز عبور کاربر

- **Backend**:
    - `DB_HOST`: آدرس دیتابیس (در Docker: `db`)
    - `DB_PORT`: پورت دیتابیس (3306)
    - `DB_NAME`: نام دیتابیس
    - `DB_USER`: نام کاربری دیتابیس
    - `DB_PASSWORD`: رمز عبور دیتابیس
    - `PORT`: پورت بک‌اند (8080)

### تنظیمات Spring Boot

در فایل `backend/src/main/resources/application.yml`:

- **Datasource**: تنظیمات اتصال به MySQL
- **JPA/Hibernate**: تنظیمات ORM
- **Server**: پورت و تنظیمات سرور
- **Logging**: سطح لاگ‌ها
- **Springdoc**: مسیرهای Swagger

### تنظیمات Frontend

در فایل `frontend/public/config.js`:

```javascript
window.BACKEND_BASE_URL = 'http://localhost:8080';
```

برای Docker Compose می‌توانید از:

```javascript
window.BACKEND_BASE_URL = 'http://backend:8080';
```

استفاده کنید (با مونت کردن فایل).

## تست‌ها

### اجرای تست‌های واحد

```bash
cd backend
mvn test
```

### تست دستی API

1. **استفاده از Swagger UI**:
    - به آدرس http://localhost:8080/swagger-ui.html بروید
    - APIها را مستقیماً از رابط کاربری تست کنید

2. **استفاده از Postman یا curl**:
   ```bash
   # دریافت لیست هتل‌ها
   curl http://localhost:8080/api/hotels
   
   # ثبت رزرو
   curl -X POST http://localhost:8080/api/reservations \
     -H "Content-Type: application/json" \
     -d '{
       "roomId": 1,
       "guestName": "علی احمدی",
       "guestEmail": "ali@example.com",
       "startDate": "2025-01-15",
       "endDate": "2025-01-20"
     }'
   ```

## امنیت

### نکات امنیتی فعلی

- **CORS**: فعال شده برای ارتباط فرانت‌اند و بک‌اند
- **Validation**: اعتبارسنجی ورودی‌ها با Spring Validation
- **Exception Handling**: مدیریت خطاها با GlobalExceptionHandler

### پیشنهادات برای بهبود امنیت

- **احراز هویت**: پیاده‌سازی JWT یا OAuth2
- **رمزنگاری رمز عبور**: استفاده از BCrypt
- **HTTPS**: فعال‌سازی SSL/TLS در production
- **Rate Limiting**: محدود کردن تعداد درخواست‌ها
- **Input Sanitization**: پاک‌سازی ورودی‌ها برای جلوگیری از XSS
- **SQL Injection Prevention**: استفاده از Prepared Statements (که با JPA خودکار است)

## بهینه‌سازی‌ها

### پیشنهادات برای بهبود عملکرد

1. **Caching**: استفاده از Redis برای کش کردن داده‌های پرتکرار
2. **Database Indexing**: ایجاد ایندکس برای فیلدهای پرجستجو
3. **Pagination**: پیاده‌سازی صفحه‌بندی برای لیست‌های بزرگ
4. **Lazy Loading**: بهینه‌سازی بارگذاری روابط JPA
5. **CDN**: استفاده از CDN برای فایل‌های استاتیک
6. **Compression**: فعال‌سازی Gzip برای پاسخ‌های HTTP

## توسعه و نگهداری

### افزودن ویژگی جدید

1. **ایجاد Entity جدید** در پکیج `entity`
2. **ایجاد Repository** در پکیج `repository`
3. **ایجاد Service** در پکیج `service`
4. **ایجاد Controller** در پکیج `controller`
5. **ایجاد DTO** در پکیج `dto` (در صورت نیاز)
6. **به‌روزرسانی Frontend** برای استفاده از API جدید

### لاگ‌ها

لاگ‌های سیستم در خروجی کانتینرها قابل مشاهده است:

```bash
# مشاهده لاگ تمام سرویس‌ها
docker compose logs

# مشاهده لاگ یک سرویس خاص
docker compose logs backend
docker compose logs frontend
docker compose logs db
```

### پشتیبان‌گیری از دیتابیس

```bash
# Export دیتابیس
docker exec hotel_mysql mysqldump -u hotel -ppassword hotel_db > backup.sql

# Import دیتابیس
docker exec -i hotel_mysql mysql -u hotel -ppassword hotel_db < backup.sql
```

## مشکلات رایج و راه‌حل‌ها

### مشکل: بک‌اند نمی‌تواند به دیتابیس متصل شود

**راه‌حل**:

- بررسی اینکه کانتینر MySQL در حال اجرا است
- بررسی متغیرهای محیطی در `docker-compose.yml`
- بررسی لاگ‌های بک‌اند: `docker compose logs backend`

### مشکل: CORS Error در مرورگر

**راه‌حل**:

- بررسی اینکه `@CrossOrigin` در کنترلرها وجود دارد
- بررسی تنظیمات Nginx برای proxy کردن درخواست‌ها

### مشکل: فایل‌های فرانت‌اند به‌روز نمی‌شوند

**راه‌حل**:

- Rebuild کردن image فرانت‌اند: `docker compose build frontend`
- Restart کردن کانتینر: `docker compose restart frontend`

## نسخه‌ها و وابستگی‌ها

- **Java**: 17
- **Spring Boot**: 3.3.3
- **MySQL**: 8.4
- **Maven**: 3.9.9
- **Docker Compose**: 3.9
- **Nginx**: Alpine (آخرین نسخه)

## مجوز و حق نشر

این پروژه برای اهداف آموزشی و یادگیری طراحی شده است.

## پشتیبانی

برای سوالات و مشکلات:

- بررسی مستندات کامل در فایل‌های دیگر
- بررسی لاگ‌های سیستم
- مراجعه به فایل `REFERENCES.md` برای منابع بیشتر

---

**نکته**: برای اطلاعات بیشتر درباره هر یک از تکنولوژی‌های استفاده شده، به فایل‌های مستندات جداگانه مراجعه کنید:

- `DOCKER_DOCS.md`: مستندات Docker
- `HTML_DOCS.md`: مستندات HTML
- `JAVASCRIPT_DOCS.md`: مستندات JavaScript
- `JAVA_DOCS.md`: مستندات Java
- `MYSQL_DOCS.md`: مستندات MySQL
- `SPRINGBOOT_DOCS.md`: مستندات Spring Boot
- `PROJECT_STRUCTURE.md`: ساختار دقیق پروژه
- `REFERENCES.md`: منابع و لینک‌های مفید

