/**
 * فایل تنظیمات برای تعیین آدرس بک‌اند
 * در Docker، nginx درخواست‌های /api را به backend proxy می‌کند
 * در محیط محلی، از localhost:8080 استفاده می‌شود
 */
// استفاده از مسیر نسبی در Docker (nginx proxy می‌کند) یا localhost در محیط محلی
window.BACKEND_BASE_URL = window.BACKEND_BASE_URL || (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
    ? 'http://localhost:8080'
    : ''); // در Docker از مسیر نسبی استفاده می‌کنیم چون nginx proxy می‌کند


