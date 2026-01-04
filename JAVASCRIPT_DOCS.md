# مستندات کامل JavaScript

## مقدمه

JavaScript یک زبان برنامه‌نویسی سطح بالا، تفسیری و چندپارادایمی است که عمدتاً برای توسعه وب استفاده می‌شود. این زبان
امکان تعامل، انیمیشن و به‌روزرسانی محتوای صفحات وب را فراهم می‌کند.

## تاریخچه JavaScript

### پیشینه

- **1995**: برندان آیک (Brendan Eich) JavaScript را در 10 روز برای Netscape ایجاد کرد
- **1996**: JavaScript به ECMAScript استانداردسازی شد
- **1997**: ECMAScript 1 (ES1) منتشر شد
- **1998-1999**: ES2 و ES3 منتشر شدند
- **2009**: ES5 منتشر شد با ویژگی‌های مهم
- **2015**: ES6 (ES2015) منتشر شد - انقلاب در JavaScript
- **2016-2023**: ES2016 تا ES2023 با ویژگی‌های جدید هر سال

### نقاط عطف

- **ES6 (2015)**: کلاس‌ها، Arrow Functions، Template Literals، Destructuring
- **ES2017**: Async/Await
- **ES2018**: Spread Operator، Rest Parameters
- **ES2020**: Optional Chaining، Nullish Coalescing
- **ES2022**: Top-level Await، Private Fields

## مفاهیم پایه JavaScript

### متغیرها

```javascript
// var (قدیمی - استفاده نکنید)
var oldVariable = "value";

// let (قابل تغییر)
let changeable = "can change";
changeable = "new value";

// const (ثابت)
const constant = "cannot change";
```

### انواع داده (Data Types)

#### Primitive Types

```javascript
// String
const name = "علی";

// Number
const age = 25;
const price = 99.99;

// Boolean
const isActive = true;

// Null
const empty = null;

// Undefined
let notDefined;

// Symbol (ES6)
const sym = Symbol('unique');
```

#### Reference Types

```javascript
// Object
const user = {
    name: "علی",
    age: 25
};

// Array
const numbers = [1, 2, 3, 4];

// Function
function greet() {
    return "Hello";
}
```

### توابع (Functions)

#### Function Declaration

```javascript
function greet(name) {
    return `سلام ${name}`;
}
```

#### Function Expression

```javascript
const greet = function(name) {
    return `سلام ${name}`;
};
```

#### Arrow Function (ES6)

```javascript
const greet = (name) => {
    return `سلام ${name}`;
};

// یا به صورت کوتاه
const greet = name => `سلام ${name}`;
```

#### Async Function

```javascript
async function fetchData() {
    const response = await fetch('/api/data');
    const data = await response.json();
    return data;
}
```

### Objects و Arrays

#### Object

```javascript
const hotel = {
    id: 1,
    name: "هتل عباسی",
    city: "اصفهان",
    getInfo: function() {
        return `${this.name} - ${this.city}`;
    }
};

// دسترسی به ویژگی‌ها
hotel.name;
hotel['name'];

// Destructuring
const { name, city } = hotel;
```

#### Array

```javascript
const hotels = ["هتل 1", "هتل 2", "هتل 3"];

// روش‌های متداول
hotels.push("هتل 4");        // افزودن
hotels.pop();                 // حذف آخرین
hotels.map(h => h.toUpperCase()); // تبدیل
hotels.filter(h => h.includes("عباسی")); // فیلتر
hotels.find(h => h === "هتل 1"); // پیدا کردن
```

## JavaScript در این پروژه

### DOM Manipulation

```javascript
// انتخاب المان
const $ = (s) => document.querySelector(s);
const hotelSelect = $('#hotelSelect');

// ایجاد المان
const card = document.createElement('div');
card.className = 'card';
card.innerHTML = '<h3>هتل جدید</h3>';

// افزودن به DOM
roomsGrid.appendChild(card);
```

### Event Handling

```javascript
// Event Listener
searchBtn.addEventListener('click', searchRooms);

// Inline Event
<button onclick="searchRooms()">جستجو</button>

// Event Delegation
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('btn')) {
        handleButtonClick(e.target);
    }
});
```

### Fetch API (ارتباط با Backend)

```javascript
// GET Request
async function loadHotels() {
    try {
        const res = await fetch(api('/api/hotels'));
        const data = await res.json();
        return data;
    } catch (error) {
        console.error('خطا:', error);
    }
}

// POST Request
async function reserve() {
    const res = await fetch(api('/api/reservations'), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            roomId: selectedRoomId,
            guestName: guestName.value,
            startDate: fromDate.value,
            endDate: toDate.value
        })
    });
    
    if (!res.ok) {
        throw new Error('خطا در ثبت رزرو');
    }
    
    const data = await res.json();
    return data;
}
```

### Async/Await Pattern

```javascript
// استفاده از async/await برای مدیریت asynchronous operations
async function searchRooms() {
    const hotelId = Number(hotelSelect.value);
    const from = fromDate.value;
    const to = toDate.value;
    
    try {
        const url = api(`/api/hotels/${hotelId}/rooms?available=true&from=${from}&to=${to}`);
        const res = await fetch(url);
        
        if (!res.ok) throw new Error();
        
        const data = await res.json();
        renderRooms(data);
    } catch (error) {
        showError('خطا در جستجوی اتاق‌ها');
    }
}
```

### Helper Functions

```javascript
// تابع کمکی برای ساخت URL
const api = (path) => `${window.BACKEND_BASE_URL}${path}`;

// تابع نمایش خطا
function showError(text) {
    message.textContent = text;
    message.className = 'err';
}

// تابع نمایش موفقیت
function showOk(text) {
    message.textContent = text;
    message.className = 'ok';
}

// تابع فرمت تاریخ
function formatDate(value) {
    if (!value) return '—';
    try {
        return new Date(value).toLocaleDateString('fa-IR', {
            year: 'numeric',
            month: 'short',
            day: '2-digit'
        });
    } catch {
        return value;
    }
}
```

### Rendering Functions

```javascript
// رندر کردن لیست اتاق‌ها
function renderRooms(rooms) {
    roomsGrid.innerHTML = '';
    
    if (!rooms.length) {
        const div = document.createElement('div');
        div.className = 'card';
        div.textContent = 'اتاقی یافت نشد';
        roomsGrid.appendChild(div);
        return;
    }
    
    for (const r of rooms) {
        const card = document.createElement('div');
        card.className = 'card room-card';
        card.innerHTML = `
            <div class="title">
                <b>اتاق ${r.number}</b>
                <span class="pill">${r.type}</span>
            </div>
            <p class="muted">ظرفیت: ${r.capacity} نفر</p>
            <p class="price">${r.pricePerNight.toLocaleString('fa-IR')} تومان / شب</p>
            <div class="actions">
                <button class="btn primary" data-id="${r.id}" data-label="${r.number}">
                    انتخاب
                </button>
            </div>
        `;
        
        card.querySelector('button').addEventListener('click', (ev) => {
            selectedRoomId = Number(ev.currentTarget.dataset.id);
            selectedRoomSpan.textContent = ev.currentTarget.dataset.label;
            reservationForm.classList.remove('hidden');
            showOk('اتاق انتخاب شد.');
        });
        
        roomsGrid.appendChild(card);
    }
}
```

## مفاهیم پیشرفته

### Closures

```javascript
function createCounter() {
    let count = 0;
    return function() {
        count++;
        return count;
    };
}

const counter = createCounter();
counter(); // 1
counter(); // 2
```

### Promises

```javascript
const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
        resolve('موفق');
    }, 1000);
});

promise
    .then(result => console.log(result))
    .catch(error => console.error(error));
```

### Template Literals

```javascript
const name = "علی";
const message = `سلام ${name}، خوش آمدید!`;

// Multi-line
const html = `
    <div>
        <h1>${title}</h1>
        <p>${content}</p>
    </div>
`;
```

### Destructuring

```javascript
// Array Destructuring
const [first, second] = [1, 2, 3];

// Object Destructuring
const { name, age } = user;

// Nested Destructuring
const { room: { number, type } } = reservation;
```

### Spread Operator

```javascript
// Array
const arr1 = [1, 2, 3];
const arr2 = [...arr1, 4, 5];

// Object
const user1 = { name: "علی", age: 25 };
const user2 = { ...user1, email: "ali@example.com" };
```

### Optional Chaining (ES2020)

```javascript
const roomNumber = reservation?.room?.number;
const city = hotel?.address?.city;
```

### Nullish Coalescing (ES2020)

```javascript
const name = user.name ?? "نامشخص";
const price = room.price ?? 0;
```

## الگوهای طراحی در این پروژه

### Module Pattern

```javascript
// config.js
window.BACKEND_BASE_URL = 'http://localhost:8080';

// app.js
const api = (path) => `${window.BACKEND_BASE_URL}${path}`;
```

### Event-Driven Architecture

```javascript
// Event listeners در انتهای فایل
searchBtn.addEventListener('click', searchRooms);
reserveBtn.addEventListener('click', reserve);
loadHotels(); // Initial load
```

### Separation of Concerns

- **config.js**: تنظیمات
- **app.js**: منطق اصلی
- **user.js, flight.js, etc.**: منطق خاص هر بخش

## بهترین روش‌ها (Best Practices)

### 1. استفاده از const و let به جای var

```javascript
// خوب
const name = "علی";
let age = 25;

// بد
var name = "علی";
```

### 2. استفاده از Arrow Functions

```javascript
// خوب
const greet = name => `سلام ${name}`;

// بد
const greet = function(name) {
    return "سلام " + name;
};
```

### 3. Error Handling

```javascript
async function fetchData() {
    try {
        const res = await fetch('/api/data');
        if (!res.ok) throw new Error('خطا در دریافت داده');
        return await res.json();
    } catch (error) {
        console.error('خطا:', error);
        showError('خطا در ارتباط با سرور');
    }
}
```

### 4. استفاده از Template Literals

```javascript
// خوب
const message = `سلام ${name}، خوش آمدید!`;

// بد
const message = "سلام " + name + "، خوش آمدید!";
```

### 5. Validation

```javascript
function validateForm() {
    if (!guestName.value || !guestEmail.value) {
        showError('تمام فیلدها را پر کنید');
        return false;
    }
    
    if (!isValidEmail(guestEmail.value)) {
        showError('ایمیل معتبر نیست');
        return false;
    }
    
    return true;
}
```

### 6. Code Organization

```javascript
// گروه‌بندی توابع مرتبط
// 1. Helper Functions
// 2. DOM Manipulation
// 3. API Calls
// 4. Event Handlers
// 5. Initialization
```

## Debugging

### Console Methods

```javascript
console.log('اطلاعات عمومی');
console.error('خطا');
console.warn('هشدار');
console.table(data); // نمایش داده به صورت جدول
console.group('گروه');
console.time('timer'); // اندازه‌گیری زمان
```

### Browser DevTools

- **Console**: اجرای کد و مشاهده خطاها
- **Sources**: Debugging و breakpoints
- **Network**: مشاهده درخواست‌های HTTP
- **Application**: Local Storage, Session Storage

## مشکلات رایج و راه‌حل‌ها

### مشکل: CORS Error

**راه‌حل**:

- بررسی تنظیمات CORS در backend
- استفاده از proxy در development

### مشکل: undefined یا null

**راه‌حل**:

```javascript
// استفاده از Optional Chaining
const value = obj?.property?.nested;

// استفاده از Default Value
const name = user.name ?? "نامشخص";
```

### مشکل: Async/Await در Loop

**راه‌حل**:

```javascript
// استفاده از Promise.all
const results = await Promise.all(
    items.map(item => fetchData(item))
);
```

## نسخه‌های JavaScript

- **ES5 (2009)**: پایه و اساس
- **ES6/ES2015**: انقلاب JavaScript
- **ES2016-ES2023**: به‌روزرسانی‌های سالانه

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی JavaScript است.

---

**نکته**: JavaScript یک زبان قدرتمند و انعطاف‌پذیر است. همیشه از آخرین ویژگی‌های ES6+ استفاده کنید و کد خود را تمیز و
قابل نگهداری نگه دارید.

