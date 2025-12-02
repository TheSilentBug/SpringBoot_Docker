# استفاده از image سبک Nginx Alpine برای سرو فایل‌های استاتیک
FROM nginx:alpine # استفاده از Nginx مینیمال
# تنظیم دایرکتوری کاری
WORKDIR /usr/share/nginx/html # مسیر پیش‌فرض سرو فایل‌ها
# کپی فایل‌های frontend به دایرکتوری Nginx
COPY public/ ./ # انتقال خروجی استاتیک به مسیر کاری
# تعیین پورت که Nginx روی آن اجرا می‌شود
EXPOSE 80 # باز کردن پورت HTTP


