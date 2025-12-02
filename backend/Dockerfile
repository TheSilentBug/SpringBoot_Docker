# مرحله Build: کامپایل و ساخت فایل JAR
# استفاده از image Maven با Java 17 برای ساخت پروژه
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
# کپی فایل pom.xml برای دانلود وابستگی‌ها
COPY pom.xml .
# دانلود تمام وابستگی‌ها (بدون اجرای تست‌ها)
RUN mvn -q -e -DskipTests dependency:go-offline
# کپی کدهای منبع
COPY src ./src
# کامپایل و ساخت فایل JAR (بدون اجرای تست‌ها)
RUN mvn -q -DskipTests package

# مرحله Runtime: اجرای برنامه
# استفاده از image سبک‌تر فقط با JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
# کپی فایل JAR ساخته شده از مرحله build
COPY --from=build /app/target/hotel-reservation-0.0.1-SNAPSHOT.jar app.jar
# تعیین پورت که برنامه روی آن اجرا می‌شود
EXPOSE 8080
# متغیر محیطی برای تنظیمات JVM (اختیاری)
ENV JAVA_OPTS=""
# دستور اجرای برنامه
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


