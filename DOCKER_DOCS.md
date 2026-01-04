# مستندات کامل Docker

## مقدمه

Docker یک پلتفرم کانتینری‌سازی است که به توسعه‌دهندگان و مدیران سیستم اجازه می‌دهد تا اپلیکیشن‌ها را به همراه تمام
وابستگی‌هایشان در یک محیط ایزوله و قابل حمل بسته‌بندی کنند. این تکنولوژی انقلابی در نحوه توسعه، تست و استقرار نرم‌افزار
ایجاد کرده است.

## تاریخچه Docker

### پیشینه

مفهوم کانتینری‌سازی ریشه در دهه 1970 دارد، زمانی که سیستم‌عامل Unix از chroot استفاده می‌کرد. اما Docker به شکل امروزی
آن در سال 2013 توسط Solomon Hykes و تیمش در شرکت dotCloud (بعداً Docker Inc.) معرفی شد.

### نقاط عطف

- **2013**: انتشار نسخه اولیه Docker به صورت open source
- **2014**: معرفی Docker Compose برای مدیریت چند کانتینر
- **2015**: معرفی Docker Swarm برای orchestration
- **2016**: معرفی Docker Machine و Docker Desktop
- **2017**: ادغام با Moby Project
- **2020**: معرفی Docker Desktop برای Mac و Windows با پشتیبانی کامل

## مفاهیم پایه Docker

### Container (کانتینر)

کانتینر یک محیط اجرایی سبک و قابل حمل است که شامل:

- کد اپلیکیشن
- runtime
- کتابخانه‌های سیستم
- تنظیمات
- تمام وابستگی‌های مورد نیاز

کانتینرها از هسته سیستم‌عامل میزبان استفاده می‌کنند و به همین دلیل بسیار سبک‌تر از ماشین‌های مجازی هستند.

### Image (ایمیج)

ایمیج یک الگوی read-only است که برای ساخت کانتینر استفاده می‌شود. ایمیج شامل:

- سیستم‌عامل پایه (مانند Ubuntu, Alpine)
- نرم‌افزارهای نصب شده
- کد اپلیکیشن
- تنظیمات

### Dockerfile

Dockerfile یک فایل متنی است که دستورالعمل‌های ساخت یک ایمیج را تعریف می‌کند. هر دستور یک لایه (layer) در ایمیج ایجاد
می‌کند.

### Docker Compose

Docker Compose یک ابزار برای تعریف و اجرای اپلیکیشن‌های چندکانتینری است. با استفاده از فایل YAML، می‌توانید چندین سرویس
را به صورت همزمان مدیریت کنید.

### Volume

Volume مکانی برای ذخیره داده‌های پایدار است که حتی پس از حذف کانتینر باقی می‌ماند. این برای ذخیره داده‌های دیتابیس و
فایل‌های مهم استفاده می‌شود.

### Network

شبکه Docker به کانتینرها اجازه می‌دهد تا با یکدیگر ارتباط برقرار کنند. به صورت پیش‌فرض، Docker یک شبکه bridge ایجاد
می‌کند.

## نحوه کار Docker

### معماری Docker

```
┌─────────────────────────────────────┐
│         Docker Engine                │
│  ┌──────────┐      ┌──────────┐    │
│  │  Client  │      │  Daemon   │    │
│  └────┬─────┘      └─────┬────┘    │
│       │                  │          │
│       └────────┬─────────┘          │
│                │                     │
│         ┌──────▼──────┐             │
│         │   Images    │             │
│         └──────┬──────┘             │
│                │                     │
│         ┌──────▼──────┐             │
│         │ Containers  │             │
│         └─────────────┘             │
└─────────────────────────────────────┘
```

### فرآیند اجرای یک کانتینر

1. **دریافت یا ساخت Image**: Docker ابتدا ایمیج را از registry (مانند Docker Hub) دریافت می‌کند یا از Dockerfile می‌سازد
2. **ایجاد Container**: از ایمیج یک کانتینر جدید ایجاد می‌شود
3. **اجرای Container**: کانتینر در یک محیط ایزوله اجرا می‌شود
4. **مدیریت چرخه حیات**: Docker چرخه حیات کانتینر را مدیریت می‌کند

### Namespace و Cgroups

Docker از دو ویژگی اصلی لینوکس استفاده می‌کند:

- **Namespace**: ایزوله کردن منابع (فایل سیستم، شبکه، پردازش‌ها)
- **Cgroups**: محدود کردن و کنترل استفاده از منابع (CPU، حافظه، I/O)

## Dockerfile در این پروژه

### Backend Dockerfile

```dockerfile
# مرحله Build: کامپایل و ساخت فایل JAR
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# مرحله Runtime: اجرای برنامه
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/hotel-reservation-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

**توضیحات**:

- **Multi-stage build**: استفاده از دو مرحله برای کاهش حجم نهایی ایمیج
- **Build stage**: استفاده از Maven برای کامپایل و ساخت JAR
- **Runtime stage**: استفاده از JRE سبک‌تر برای اجرا
- **EXPOSE**: اعلام پورت 8080 برای دسترسی خارجی
- **ENTRYPOINT**: دستور نهایی برای اجرای برنامه

### Frontend Dockerfile

```dockerfile
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY public/ ./
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
```

**توضیحات**:

- **nginx:alpine**: استفاده از ایمیج سبک Nginx
- **COPY**: کپی فایل‌های استاتیک و تنظیمات
- **EXPOSE 80**: پورت HTTP استاندارد

## Docker Compose در این پروژه

### ساختار docker-compose.yml

```yaml
version: "3.9"
services:
  db:              # سرویس دیتابیس MySQL
  backend:         # سرویس بک‌اند Spring Boot
  frontend:        # سرویس فرانت‌اند Nginx
volumes:
  db_data:         # Volume برای ذخیره داده‌های دیتابیس
```

### سرویس MySQL

```yaml
db:
  image: mysql:8.4
  container_name: hotel_mysql
  restart: unless-stopped
  environment:
    MYSQL_ROOT_PASSWORD: password
    MYSQL_DATABASE: hotel_db
    MYSQL_USER: hotel
    MYSQL_PASSWORD: password
  ports:
    - "3306:3306"
  volumes:
    - db_data:/var/lib/mysql
  command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  healthcheck:
    test: ["CMD-SHELL", "mysqladmin ping -h localhost -u$MYSQL_USER -p$MYSQL_PASSWORD || exit 1"]
    interval: 5s
    timeout: 5s
    retries: 20
```

**ویژگی‌ها**:

- **restart: unless-stopped**: راه‌اندازی مجدد خودکار
- **healthcheck**: بررسی سلامت سرویس
- **volumes**: ذخیره داده‌ها در volume پایدار
- **command**: تنظیمات charset برای پشتیبانی از فارسی

### سرویس Backend

```yaml
backend:
  build: ./backend
  container_name: hotel_backend
  depends_on:
    db:
      condition: service_healthy
  environment:
    DB_HOST: db
    DB_PORT: 3306
    DB_NAME: hotel_db
    DB_USER: hotel
    DB_PASSWORD: password
    PORT: 8080
  ports:
    - "8080:8080"
```

**ویژگی‌ها**:

- **build**: ساخت ایمیج از Dockerfile
- **depends_on**: انتظار برای آماده شدن دیتابیس
- **condition: service_healthy**: انتظار تا دیتابیس سالم شود

### سرویس Frontend

```yaml
frontend:
  build: ./frontend
  container_name: hotel_frontend
  depends_on:
    - backend
  ports:
    - "8081:80"
  volumes:
    - ./frontend/public/config.js:/usr/share/nginx/html/config.js:ro
```

**ویژگی‌ها**:

- **depends_on**: انتظار برای راه‌اندازی بک‌اند
- **volumes**: مونت کردن فایل config برای تغییر بدون rebuild

## دستورات مهم Docker

### مدیریت کانتینرها

```bash
# اجرای یک کانتینر
docker run <image-name>

# لیست کانتینرهای در حال اجرا
docker ps

# لیست تمام کانتینرها (شامل متوقف شده‌ها)
docker ps -a

# توقف یک کانتینر
docker stop <container-id>

# شروع مجدد کانتینر
docker start <container-id>

# حذف کانتینر
docker rm <container-id>

# مشاهده لاگ‌ها
docker logs <container-id>

# اجرای دستور در کانتینر
docker exec -it <container-id> <command>
```

### مدیریت ایمیجها

```bash
# لیست ایمیجها
docker images

# ساخت ایمیج از Dockerfile
docker build -t <image-name> <path>

# حذف ایمیج
docker rmi <image-id>

# Pull ایمیج از Docker Hub
docker pull <image-name>

# Push ایمیج به registry
docker push <image-name>
```

### Docker Compose

```bash
# راه‌اندازی تمام سرویس‌ها
docker compose up

# راه‌اندازی در پس‌زمینه
docker compose up -d

# ساخت ایمیج‌ها
docker compose build

# ساخت و راه‌اندازی
docker compose up --build

# توقف سرویس‌ها
docker compose down

# توقف و حذف volumes
docker compose down -v

# مشاهده لاگ‌ها
docker compose logs

# مشاهده لاگ یک سرویس خاص
docker compose logs <service-name>

# اجرای دستور در یک سرویس
docker compose exec <service-name> <command>

# مشاهده وضعیت سرویس‌ها
docker compose ps
```

## مزایای Docker

### 1. سازگاری (Consistency)

- محیط یکسان در development، testing و production
- "Works on my machine" دیگر مشکلی نیست

### 2. ایزوله‌سازی (Isolation)

- هر اپلیکیشن در محیط خودش اجرا می‌شود
- عدم تداخل بین اپلیکیشن‌ها

### 3. قابلیت حمل (Portability)

- اجرا روی هر سیستم‌عاملی که Docker دارد
- انتقال آسان بین سرورها

### 4. مقیاس‌پذیری (Scalability)

- اجرای چندین نمونه از یک کانتینر
- استفاده از orchestration tools مانند Kubernetes

### 5. کارایی (Efficiency)

- استفاده بهینه از منابع
- راه‌اندازی سریع‌تر از VM

### 6. مدیریت وابستگی‌ها

- تمام وابستگی‌ها در ایمیج گنجانده شده
- عدم نیاز به نصب دستی

## معایب و محدودیت‌ها

### 1. امنیت

- استفاده از هسته مشترک سیستم‌عامل
- نیاز به به‌روزرسانی منظم

### 2. پیچیدگی

- نیاز به یادگیری مفاهیم جدید
- مدیریت شبکه و storage پیچیده‌تر می‌شود

### 3. Windows/Mac

- نیاز به VM در سیستم‌های غیر Linux
- عملکرد کمی کندتر

### 4. Debugging

- عیب‌یابی در کانتینرها می‌تواند چالش‌برانگیز باشد

## بهترین روش‌ها (Best Practices)

### 1. استفاده از Multi-stage Build

```dockerfile
# Build stage
FROM maven:3.9 AS build
WORKDIR /app
COPY . .
RUN mvn package

# Runtime stage
FROM openjdk:17-jre
COPY --from=build /app/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. استفاده از .dockerignore

```
target/
.git/
*.md
.env
```

### 3. بهینه‌سازی لایه‌ها

- دستورات مشابه را ترکیب کنید
- تغییرات کم‌تکرار را در انتها قرار دهید

### 4. استفاده از Volume برای داده‌های پایدار

```yaml
volumes:
  - db_data:/var/lib/mysql
```

### 5. محدود کردن منابع

```yaml
deploy:
  resources:
    limits:
      cpus: '0.5'
      memory: 512M
```

### 6. استفاده از Health Checks

```yaml
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
  interval: 30s
  timeout: 10s
  retries: 3
```

## Docker در این پروژه

### جریان کار

1. **توسعه**: کد در سیستم محلی نوشته می‌شود
2. **Build**: Dockerfile برای ساخت ایمیج استفاده می‌شود
3. **Test**: کانتینرها برای تست اجرا می‌شوند
4. **Deploy**: ایمیج‌ها به production منتقل می‌شوند

### مزایا برای این پروژه

- **سهولت راه‌اندازی**: یک دستور برای راه‌اندازی تمام سرویس‌ها
- **ایزوله‌سازی**: MySQL، Backend و Frontend جدا از هم
- **مدیریت وابستگی‌ها**: تمام وابستگی‌ها در ایمیج‌ها
- **قابلیت توسعه**: افزودن سرویس جدید آسان است
- **مدیریت داده**: Volume برای ذخیره داده‌های دیتابیس

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی Docker است.

---

**نکته**: Docker یک تکنولوژی در حال تکامل است و به‌طور مداوم ویژگی‌های جدید اضافه می‌شود. برای به‌روزترین اطلاعات، به
مستندات رسمی Docker مراجعه کنید.

