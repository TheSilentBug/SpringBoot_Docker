# استفاده از image سبک Nginx Alpine برای سرو فایل‌های استاتیک
FROM nginx:alpine
# تنظیم دایرکتوری کاری
WORKDIR /usr/share/nginx/html
# کپی فایل‌های frontend به دایرکتوری Nginx
COPY public/ ./
# تعیین پورت که Nginx روی آن اجرا می‌شود
EXPOSE 80


