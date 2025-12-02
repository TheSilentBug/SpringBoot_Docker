# مرحله Build: کامپایل و ساخت فایل JAR
# استفاده از image Maven با Java 17 برای ساخت پروژه
FROM maven:3.9.9-eclipse-temurin-17 AS build # مرحله build با Maven و JDK 17
WORKDIR /app # تعیین دایرکتوری کاری داخل کانتینر
# کپی فایل pom.xml برای دانلود وابستگی‌ها
COPY pom.xml . # انتقال POM به کانتینر
# دانلود تمام وابستگی‌ها (بدون اجرای تست‌ها)
RUN mvn -q -e -DskipTests dependency:go-offline # دانلود dependencies برای کش
# کپی کدهای منبع
COPY src ./src # کپی سورس به کانتینر
# کامپایل و ساخت فایل JAR (بدون اجرای تست‌ها)
RUN mvn -q -DskipTests package # ساخت فایل JAR بدون تست

# مرحله Runtime: اجرای برنامه
# استفاده از image سبک‌تر فقط با JRE
FROM eclipse-temurin:17-jre # مرحله اجرا با JRE سبک
WORKDIR /app # تنظیم دایرکتوری کاری
# کپی فایل JAR ساخته شده از مرحله build
COPY --from=build /app/target/hotel-reservation-0.0.1-SNAPSHOT.jar app.jar # انتقال JAR نهایی
# تعیین پورت که برنامه روی آن اجرا می‌شود
EXPOSE 8080 # اعلام پورت سرویس
# متغیر محیطی برای تنظیمات JVM (اختیاری)
ENV JAVA_OPTS="" # متغیر قابل تنظیم برای پارامترهای JVM
# دستور اجرای برنامه
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] # فرمان نهایی برای اجرای برنامه


