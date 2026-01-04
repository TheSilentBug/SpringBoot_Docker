# مستندات کامل HTML

## مقدمه

HTML (HyperText Markup Language) زبان نشانه‌گذاری استاندارد برای ایجاد صفحات وب است. HTML ساختار و محتوای صفحات وب را
تعریف می‌کند و پایه و اساس تمام وب‌سایت‌ها محسوب می‌شود.

## تاریخچه HTML

### پیشینه

- **1991**: تیم برنرز-لی HTML را اختراع کرد
- **1993**: HTML 1.0 منتشر شد
- **1995**: HTML 2.0 با پشتیبانی از فرم‌ها
- **1997**: HTML 3.2 و 4.0 منتشر شد
- **2000**: XHTML 1.0 (HTML به صورت XML)
- **2008**: HTML5 شروع به توسعه کرد
- **2014**: HTML5 به عنوان استاندارد رسمی منتشر شد
- **2016**: HTML 5.1 منتشر شد
- **2017**: HTML 5.2 منتشر شد

### HTML5

HTML5 انقلابی در توسعه وب ایجاد کرد با ویژگی‌های جدید مانند:

- عناصر معنایی (semantic elements)
- پشتیبانی از audio و video
- Canvas برای گرافیک
- Local Storage
- WebSockets
- و بسیاری دیگر

## مفاهیم پایه HTML

### ساختار یک سند HTML

```html
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>عنوان صفحه</title>
</head>
<body>
    <!-- محتوای صفحه -->
</body>
</html>
```

### تگ‌های اصلی

#### DOCTYPE

```html
<!DOCTYPE html>
```

نوع سند را مشخص می‌کند و به مرورگر می‌گوید که از HTML5 استفاده می‌کند.

#### html

```html
<html lang="fa" dir="rtl">
```

ریشه سند HTML. `lang` زبان صفحه و `dir` جهت متن (rtl برای راست‌چین) را مشخص می‌کند.

#### head

```html
<head>
    <!-- اطلاعات متا و لینک‌ها -->
</head>
```

شامل اطلاعات متا، لینک به فایل‌های CSS و JavaScript، و عنوان صفحه.

#### body

```html
<body>
    <!-- محتوای قابل مشاهده -->
</body>
```

شامل تمام محتوای قابل مشاهده صفحه.

## عناصر HTML در این پروژه

### عناصر ساختاری

#### Header

```html
<header>
    <div class="container nav-container">
        <a href="config.html" class="logo">
            <i class="fas fa-crown"></i>
            هتل مارکوپولو
        </a>
        <ul class="nav-menu">
            <li><a href="index.html">هتل</a></li>
        </ul>
    </div>
</header>
```

بخش هدر صفحه که شامل لوگو و منوی ناوبری است.

#### Section

```html
<section class="hero">
    <div class="container">
        <h1>تجربه‌ای فراموش‌نشدنی</h1>
    </div>
</section>
```

بخش‌های مختلف محتوا را جدا می‌کند.

#### Footer

```html
<footer>
    <div class="container">
        <p>© ۲۰۲۴ تمامی حقوق محفوظ است.</p>
    </div>
</footer>
```

پاورقی صفحه.

### عناصر فرم

#### Input

```html
<input type="text" placeholder="مقصد" id="destination">
<input type="date" id="checkin">
<input type="email" id="email">
```

**انواع Input**:

- `text`: متن ساده
- `date`: انتخاب تاریخ
- `email`: ایمیل
- `password`: رمز عبور
- `number`: عدد
- `checkbox`: چک‌باکس
- `radio`: رادیو باتن

#### Select

```html
<select id="hotelSelect">
    <option value="1">هتل عباسی</option>
    <option value="2">هتل اسپیناس</option>
</select>
```

لیست کشویی برای انتخاب گزینه.

#### Button

```html
<button class="btn btn-primary" onclick="searchHotels()">
    جستجو
</button>
```

دکمه برای اجرای عملیات.

#### Form

```html
<form id="reservationForm" onsubmit="handleSubmit(event)">
    <input type="text" name="guestName" required>
    <button type="submit">ثبت رزرو</button>
</form>
```

فرم برای جمع‌آوری و ارسال داده‌ها.

### عناصر چندرسانه‌ای

#### Image

```html
<img src="https://example.com/hotel.jpg" alt="هتل لوکس">
```

نمایش تصویر. `alt` برای دسترسی‌پذیری مهم است.

#### Video (HTML5)

```html
<video controls>
    <source src="video.mp4" type="video/mp4">
</video>
```

#### Audio (HTML5)

```html
<audio controls>
    <source src="audio.mp3" type="audio/mpeg">
</audio>
```

### عناصر معنایی HTML5

#### article

```html
<article>
    <h2>عنوان مقاله</h2>
    <p>محتوای مقاله...</p>
</article>
```

برای محتوای مستقل و قابل استفاده مجدد.

#### aside

```html
<aside>
    <h3>اطلاعات تکمیلی</h3>
    <p>محتوای جانبی...</p>
</aside>
```

برای محتوای جانبی.

#### nav

```html
<nav>
    <ul>
        <li><a href="index.html">خانه</a></li>
        <li><a href="about.html">درباره</a></li>
    </ul>
</nav>
```

برای منوی ناوبری.

#### main

```html
<main>
    <!-- محتوای اصلی صفحه -->
</main>
```

برای محتوای اصلی صفحه (فقط یک بار در صفحه).

## ویژگی‌های HTML در این پروژه

### Meta Tags

```html
<meta charset="UTF-8">
```

تعیین encoding برای پشتیبانی از کاراکترهای فارسی.

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

برای ریسپانسیو بودن در موبایل.

### Data Attributes

```html
<button data-id="123" data-label="اتاق 101">انتخاب</button>
```

ذخیره داده‌های سفارشی که می‌توان با JavaScript دسترسی داشت.

### Datalist

```html
<input type="text" list="cities">
<datalist id="cities">
    <option value="تهران">تهران</option>
    <option value="مشهد">مشهد</option>
</datalist>
```

لیست پیشنهادی برای input.

### ARIA Attributes

```html
<button aria-label="بستن منو" aria-expanded="false">
    <span aria-hidden="true">×</span>
</button>
```

برای بهبود دسترسی‌پذیری.

## HTML در این پروژه

### ساختار صفحات

هر صفحه در این پروژه از ساختار زیر پیروی می‌کند:

1. **DOCTYPE و html**: تعریف نوع سند
2. **head**: شامل meta tags، لینک CSS و فونت‌ها
3. **header**: نوار ناوبری و لوگو
4. **main content**: محتوای اصلی صفحه
5. **footer**: اطلاعات تماس و لینک‌ها
6. **scripts**: لینک به فایل‌های JavaScript

### مثال از index.html

```html
<!DOCTYPE html>
<html dir="rtl" lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>رزرو هتل لوکس</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <!-- نوار ناوبری -->
    </header>
    
    <section class="hero">
        <!-- بخش جستجو -->
    </section>
    
    <section class="features">
        <!-- ویژگی‌ها -->
    </section>
    
    <footer>
        <!-- پاورقی -->
    </footer>
    
    <script src="config.js"></script>
    <script src="app.js"></script>
</body>
</html>
```

### ویژگی‌های خاص استفاده شده

1. **RTL Support**: `dir="rtl"` برای راست‌چین بودن
2. **Persian Language**: `lang="fa"` برای زبان فارسی
3. **UTF-8 Encoding**: پشتیبانی کامل از کاراکترهای فارسی
4. **Responsive Meta**: برای نمایش صحیح در موبایل
5. **Font Awesome Icons**: استفاده از آیکون‌ها

## بهترین روش‌ها (Best Practices)

### 1. استفاده از Semantic HTML

```html
<!-- خوب -->
<header>
    <nav>
        <ul>
            <li><a href="index.html">خانه</a></li>
        </ul>
    </nav>
</header>

<!-- بد -->
<div class="header">
    <div class="nav">
        <div class="menu-item">خانه</div>
    </div>
</div>
```

### 2. استفاده از Alt برای تصاویر

```html
<img src="hotel.jpg" alt="نمای بیرونی هتل عباسی اصفهان">
```

### 3. ساختار منطقی Heading

```html
<h1>عنوان اصلی</h1>
  <h2>زیرعنوان</h2>
    <h3>زیرزیرعنوان</h3>
```

### 4. استفاده از Form Validation

```html
<input type="email" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">
```

### 5. دسترسی‌پذیری (Accessibility)

```html
<button aria-label="بستن" aria-expanded="true">
    <span aria-hidden="true">×</span>
</button>
```

### 6. بهینه‌سازی SEO

```html
<meta name="description" content="رزرو آنلاین هتل با بهترین قیمت">
<meta name="keywords" content="رزرو هتل, هتل, اقامت">
```

## HTML5 APIs استفاده شده

### Local Storage

```javascript
localStorage.setItem('user', JSON.stringify(userData));
const user = JSON.parse(localStorage.getItem('user'));
```

### Fetch API

```javascript
fetch('/api/hotels')
    .then(response => response.json())
    .then(data => console.log(data));
```

### History API

```javascript
history.pushState({}, '', '/new-page');
```

## اعتبارسنجی HTML

### ابزارهای اعتبارسنجی

1. **W3C Validator**: https://validator.w3.org/
2. **HTMLHint**: ابزار linting برای HTML
3. **Browser DevTools**: بررسی در کنسول مرورگر

### بررسی اعتبار

```bash
# استفاده از W3C Validator
# یا استفاده از extension در VS Code
```

## مشکلات رایج و راه‌حل‌ها

### مشکل: کاراکترهای فارسی به درستی نمایش داده نمی‌شوند

**راه‌حل**:

```html
<meta charset="UTF-8">
```

و اطمینان از اینکه فایل با encoding UTF-8 ذخیره شده است.

### مشکل: صفحه در موبایل به درستی نمایش داده نمی‌شود

**راه‌حل**:

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

### مشکل: فرم‌ها اعتبارسنجی نمی‌شوند

**راه‌حل**:

```html
<input type="email" required>
<form onsubmit="return validateForm()">
```

## نسخه‌های HTML

- **HTML 4.01**: آخرین نسخه HTML کلاسیک
- **XHTML 1.0**: HTML به صورت XML
- **HTML5**: نسخه مدرن با ویژگی‌های جدید
- **HTML 5.1, 5.2, 5.3**: به‌روزرسانی‌های HTML5

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی HTML است.

---

**نکته**: HTML پایه و اساس وب است. یادگیری صحیح HTML برای هر توسعه‌دهنده وب ضروری است. همیشه از semantic HTML استفاده
کنید و به دسترسی‌پذیری توجه داشته باشید.

