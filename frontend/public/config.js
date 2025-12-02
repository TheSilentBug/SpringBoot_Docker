/**
 * فایل تنظیمات برای تعیین آدرس بک‌اند
 * در Docker Compose، سرویس بک‌اند از طریق 'http://backend:8080' قابل دسترسی است
 * در محیط توسعه محلی، از 'http://localhost:8080' استفاده می‌شود
 */
window.BACKEND_BASE_URL = window.BACKEND_BASE_URL || 'http://localhost:8080';


