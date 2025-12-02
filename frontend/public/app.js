// تابع کمکی برای انتخاب المان‌های DOM با استفاده از querySelector
const $ = (s) => document.querySelector(s); // انتخاب اولین المان مطابق سِلِکتور

// انتخاب تمام المان‌های مورد نیاز از صفحه HTML
const hotelSelect = $('#hotelSelect'); // لیست کشویی انتخاب هتل
const fromDate = $('#fromDate'); // فیلد تاریخ شروع
const toDate = $('#toDate'); // فیلد تاریخ پایان
const searchBtn = $('#searchBtn'); // دکمه جستجو
const reservationForm = $('#reservationForm'); // فرم رزرواسیون
const selectedRoomSpan = $('#selectedRoom'); // نمایش شماره اتاق انتخاب شده
const guestName = $('#guestName'); // فیلد نام مهمان
const guestEmail = $('#guestEmail'); // فیلد ایمیل مهمان
const reserveBtn = $('#reserveBtn'); // دکمه ثبت رزرو
const message = $('#message'); // المان نمایش پیام‌ها
const hotelsGrid = $('#hotelsGrid'); // المان نمایش کارت‌های هتل‌ها
const roomsGrid = $('#roomsGrid'); // المان نمایش کارت‌های اتاق‌ها
const hotelsTableBody = $('#hotelsTableBody'); // بدنه جدول لیست هتل‌ها
const reservationIdInput = $('#reservationIdInput'); // ورودی شناسه رزرو برای جستجو
const reservationLookupBtn = $('#reservationLookupBtn'); // دکمه نمایش رزرو
const reservationDetails = $('#reservationDetails'); // ناحیه نمایش اطلاعات رزرو

// متغیر برای نگهداری شناسه اتاق انتخاب شده
let selectedRoomId = null; // نگهداری شناسه اتاق انتخاب شده

// تابع کمکی برای ساخت URL کامل API با استفاده از آدرس بک‌اند
const api = (path) => `${window.BACKEND_BASE_URL}${path}`; // ساخت URL کامل برای درخواست

/**
 * بارگذاری لیست هتل‌ها از API و نمایش آن‌ها در صفحه
 * این تابع هتل‌ها را از سرور دریافت می‌کند و آن‌ها را در لیست کشویی و کارت‌ها نمایش می‌دهد
 */
async function loadHotels() { // دریافت لیست هتل‌ها از سرور
    try {
        // درخواست GET به API برای دریافت لیست هتل‌ها
        const res = await fetch(api('/api/hotels')); // ارسال درخواست HTTP
        const data = await res.json(); // تبدیل پاسخ به JSON
        
        // پاک کردن لیست کشویی قبل از پر کردن مجدد
        hotelSelect.innerHTML = ''; // حذف گزینه‌های قبلی
        
        // اضافه کردن هر هتل به لیست کشویی
        for (const h of data) { // پیمایش تمام هتل‌ها
            const opt = document.createElement('option'); // ایجاد گزینه جدید
            opt.value = h.id; // تنظیم مقدار به شناسه هتل
            opt.textContent = `${h.name} - ${h.city}`; // نمایش نام و شهر
            hotelSelect.appendChild(opt); // افزودن گزینه به لیست
        }
        if (data.length) {
            hotelSelect.value = data[0].id; // انتخاب اولین گزینه به صورت پیش‌فرض
        }
        
        // نمایش هتل‌ها در کارت‌ها و جدول مجزا
        await renderHotelCards(data); // فراخوانی رندر کارت‌ها
        renderHotelsTable(data); // نمایش داده‌ها در جدول
    } catch (e) {
        showError('خطا در دریافت لیست هتل‌ها'); // نمایش پیام خطا
    }
}

/**
 * نمایش پیام خطا به کاربر
 * @param {string} text متن پیام خطا
 */
function showError(text) { // نمایش خطای کاربری
    message.textContent = text; // قرار دادن متن پیام
    message.className = 'err'; // کلاس CSS برای نمایش خطا با رنگ قرمز
}

/**
 * نمایش پیام موفقیت به کاربر
 * @param {string} text متن پیام موفقیت
 */
function showOk(text) { // نمایش پیام موفقیت
    message.textContent = text; // تنظیم متن پیام
    message.className = 'ok'; // کلاس CSS برای نمایش موفقیت با رنگ سبز
}

/**
 * نمایش لیست اتاق‌ها در صفحه
 * این تابع کارت‌های اتاق‌ها را ایجاد می‌کند و در صفحه نمایش می‌دهد
 * @param {Array} rooms آرایه‌ای از اشیاء اتاق
 */
function renderRooms(rooms) { // رندر کارت‌های اتاق
    // پاک کردن محتوای قبلی
    roomsGrid.innerHTML = ''; // تخلیه ظرف کارت‌ها
    
    // اگر اتاقی پیدا نشد، پیام مناسب نمایش داده می‌شود
    if (!rooms.length) { // بررسی خالی بودن آرایه
        const div = document.createElement('div'); // ساخت کارت پیام
        div.className = 'card'; // تعیین کلاس کارت
        div.textContent = 'اتاقی یافت نشد'; // متن اطلاع‌رسانی
        roomsGrid.appendChild(div); // افزودن کارت به گرید
        return; // خروج از تابع
    }
    
    // ایجاد کارت برای هر اتاق
    for (const r of rooms) { // پیمایش اتاق‌ها
        const card = document.createElement('div'); // ساخت کارت
        card.className = 'card room-card'; // تعیین کلاس‌های ظاهری
        
        // ساخت HTML کارت اتاق شامل شماره، نوع، ظرفیت و قیمت
        card.innerHTML = ` // قالب HTML داخلی کارت
			<div class="title"><b>اتاق ${r.number}</b><span class="pill">${r.type}</span></div>
			<p class="muted">ظرفیت: ${r.capacity} نفر</p>
			<p class="price">${r.pricePerNight.toLocaleString('fa-IR')} تومان / شب</p>
			<div class="actions"><button class="btn primary" data-id="${r.id}" data-label="${r.number}">انتخاب</button></div>
		`;
        
        // اضافه کردن رویداد کلیک به دکمه انتخاب اتاق
        card.querySelector('button').addEventListener('click', (ev) => { // هندلر انتخاب اتاق
            // ذخیره شناسه اتاق انتخاب شده
            selectedRoomId = Number(ev.currentTarget.dataset.id); // خواندن data-id و تبدیل به عدد
            // نمایش شماره اتاق در فرم رزرو
            selectedRoomSpan.textContent = ev.currentTarget.dataset.label; // نمایش شماره اتاق
            // نمایش فرم رزرو
            reservationForm.classList.remove('hidden'); // حذف حالت مخفی
            showOk('اتاق انتخاب شد. اطلاعات رزرو را وارد کنید.'); // اطلاع‌رسانی موفقیت انتخاب
        });
        
        // اضافه کردن کارت به صفحه
        roomsGrid.appendChild(card); // رندر کارت در شبکه
    }
}

/**
 * تکمیل جدول هتل‌ها با داده‌های دریافتی بک‌اند
 * @param {Array} hotels آرایه هتل‌ها
 */
function renderHotelsTable(hotels) { // رندر جدول هتل‌ها
    if (!hotelsTableBody) return; // اگر جدول در صفحه نباشد، کاری نکن
    hotelsTableBody.innerHTML = ''; // خالی کردن جدول
    
    if (!hotels.length) { // اگر داده‌ای وجود ندارد
        const row = document.createElement('tr'); // ساخت ردیف پیام
        row.innerHTML = `<td colspan="4" class="muted">هتلی ثبت نشده است.</td>`; // پیام اطلاع‌رسانی
        hotelsTableBody.appendChild(row); // افزودن ردیف به جدول
        return;
    }
    
    for (const hotel of hotels) { // پیمایش هتل‌ها
        const row = document.createElement('tr'); // ایجاد ردیف جدید
        row.innerHTML = `
            <td>${hotel.id}</td>
            <td>${hotel.name}</td>
            <td>${hotel.city || '—'}</td>
            <td>${hotel.address || '—'}</td>
        `;
        hotelsTableBody.appendChild(row); // افزودن ردیف به جدول
    }
}

/**
 * جستجوی اتاق‌های موجود یک هتل در بازه زمانی مشخص
 * این تابع با استفاده از API، اتاق‌های موجود را پیدا می‌کند و نمایش می‌دهد
 */
async function searchRooms() { // جستجوی اتاق‌های موجود
    // پاک کردن پیام‌های قبلی
    message.textContent = ''; // حذف متن پیام
    
    // دریافت مقادیر از فرم
    const hotelId = Number(hotelSelect.value); // شناسه هتل انتخاب شده
    const from = fromDate.value; // تاریخ شروع
    const to = toDate.value; // تاریخ پایان
    
    // بررسی اینکه تمام فیلدها پر شده‌اند
    if (!hotelId || !from || !to) { // اعتبارسنجی ورودی‌ها
        showError('هتل، تاریخ شروع و پایان را وارد کنید.'); // پیام خطا برای فیلد خالی
        return; // خروج از تابع
    }
    
    try {
        // ساخت URL برای درخواست API با پارامترهای جستجو
        const url = api(`/api/hotels/${hotelId}/rooms?available=true&from=${from}&to=${to}`); // مسیر با query string
        const res = await fetch(url); // ارسال درخواست به سرور
        
        // بررسی موفقیت درخواست
        if (!res.ok) throw new Error(); // در صورت خطا استثناء ایجاد می‌کند
        
        // دریافت داده‌های اتاق‌ها
        const data = await res.json(); // تبدیل پاسخ به JSON
        
        // نمایش اتاق‌های پیدا شده
        renderRooms(data); // رندر لیست اتاق‌ها
    } catch {
        showError('خطا در جستجوی اتاق‌ها'); // نمایش خطا در هنگام شکست
    }
}

/**
 * نمایش کارت‌های هتل‌ها در بخش پیشنهادی
 * این تابع برای هر هتل یک کارت ایجاد می‌کند و کمترین قیمت اتاق را محاسبه می‌کند
 * @param {Array} hotels آرایه‌ای از اشیاء هتل
 */
async function renderHotelCards(hotels) { // رندر کارت‌های هتل
    // پاک کردن محتوای قبلی
    hotelsGrid.innerHTML = ''; // تخلیه شبکه هتل‌ها
    
    // ایجاد کارت برای هر هتل
    for (const h of hotels) { // پیمایش هتل‌ها
        // دریافت لیست اتاق‌ها برای محاسبه کمترین قیمت
        let minPrice = null; // کمترین قیمت اولیه
        try {
            const res = await fetch(api(`/api/hotels/${h.id}/rooms`)); // دریافت اتاق‌های هتل
            if (res.ok) { // فقط در صورت پاسخ موفق
                const rooms = await res.json(); // تبدیل به JSON
                // پیدا کردن کمترین قیمت در بین تمام اتاق‌ها
                for (const r of rooms) { // پیمایش اتاق‌ها
                    if (minPrice === null || r.pricePerNight < minPrice) minPrice = r.pricePerNight; // بروزرسانی کمترین قیمت
                }
            }
        } catch {
            // در صورت خطا، minPrice همان null باقی می‌ماند
        }
        
        // ایجاد المان کارت هتل
        const card = document.createElement('div'); // ایجاد div کارت
        card.className = 'card hotel-card'; // تعیین کلاس‌های ظاهری
        
        // ساخت HTML کارت شامل نام، شهر، آدرس و کمترین قیمت
        card.innerHTML = ` // قالب HTML کارت هتل
			<div class="title">
				<b>${h.name}</b>
				<span class="stars">★★★★★</span>
			</div>
			<p class="muted">${h.city} • ${h.address || ''}</p>
			<p class="price">${minPrice ? minPrice.toLocaleString('fa-IR') + ' تومان / شب' : '—'}</p>
			<div class="actions">
				<button class="btn primary" data-id="${h.id}">مشاهده اتاق‌ها</button>
			</div>
		`;
        
        // اضافه کردن رویداد کلیک به دکمه مشاهده اتاق‌ها
        card.querySelector('button').addEventListener('click', async () => { // رویداد مشاهده اتاق‌ها
            // انتخاب هتل در لیست کشویی
            hotelSelect.value = h.id; // تنظیم گزینه انتخاب‌شده
            // جستجوی اتاق‌های این هتل
            await searchRooms(); // اجرای جستجو با هتل انتخابی
            // اسکرول به بخش نتایج
            window.scrollTo({top: document.querySelector('.results').offsetTop - 60, behavior: 'smooth'}); // اسکرول نرم به نتایج
        });
        
        // اضافه کردن کارت به صفحه
        hotelsGrid.appendChild(card); // قرار دادن کارت در DOM
    }
}

/**
 * ثبت رزرو جدید
 * این تابع اطلاعات رزرو را به API ارسال می‌کند و رزرو را ثبت می‌کند
 */
async function reserve() { // ثبت رزرو جدید
    // پاک کردن پیام‌های قبلی
    message.textContent = ''; // حذف پیام موجود
    
    // بررسی اینکه اتاقی انتخاب شده باشد
    if (!selectedRoomId) { // در صورت نبود اتاق انتخاب‌شده
        showError('ابتدا یک اتاق انتخاب کنید.'); // اطلاع به کاربر
        return; // توقف اجرا
    }
    
    // بررسی اینکه تمام فیلدهای فرم پر شده باشند
    if (!guestName.value || !guestEmail.value || !fromDate.value || !toDate.value) { // اعتبارسنجی فیلدها
        showError('تمام فیلدها را کامل کنید.'); // پیام خطا
        return; // خروج
    }
    
    try {
        // ارسال درخواست POST به API برای ایجاد رزرو
        const res = await fetch(api('/api/reservations'), { // ارسال درخواست
            method: 'POST', // متد HTTP
            headers: {'Content-Type': 'application/json'}, // تعیین نوع محتوا
            body: JSON.stringify({ // ساخت بدنه JSON
                roomId: selectedRoomId, // شناسه اتاق
                guestName: guestName.value, // نام مهمان
                guestEmail: guestEmail.value, // ایمیل مهمان
                startDate: fromDate.value, // تاریخ شروع
                endDate: toDate.value // تاریخ پایان
            })
        });
        
        // بررسی موفقیت درخواست
        if (!res.ok) { // در صورت پاسخ ناموفق
            const t = await res.text(); // خواندن متن خطا
            throw new Error(t); // پرتاب خطا برای catch
        }
        
        // دریافت پاسخ از سرور
        const data = await res.json(); // تبدیل پاسخ به JSON
        
        // نمایش پیام موفقیت با شناسه رزرو
        showOk(`رزرو با شناسه ${data.id} ثبت شد.`); // اطلاع‌رسانی موفقیت
    } catch (e) {
        showError('خطا در ثبت رزرو'); // نمایش پیام خطا
    }
}

/**
 * دریافت اطلاعات یک رزرو بر اساس شناسه وارد شده توسط کاربر
 */
async function lookupReservation() { // جستجوی رزرو واحد
    if (!reservationIdInput || !reservationDetails) return; // اگر عناصر در صفحه نیستند، کاری نکن
    reservationDetails.textContent = ''; // پاک کردن محتوای قبلی
    reservationDetails.classList.remove('ok', 'err'); // حذف حالت‌های قبلی
    
    const reservationId = Number(reservationIdInput.value); // خواندن شناسه
    if (!reservationId) { // اعتبارسنجی مقدار
        reservationDetails.textContent = 'شناسه رزرو را به صورت عددی وارد کنید.'; // پیام راهنما
        reservationDetails.classList.add('err'); // رنگ قرمز برای خطا
        return;
    }
    
    try {
        const res = await fetch(api(`/api/reservations/${reservationId}`)); // درخواست به بک‌اند
        if (!res.ok) throw new Error(); // در صورت خطا به catch می‌رود
        const data = await res.json(); // دریافت اطلاعات رزرو
        
        reservationDetails.classList.add('ok'); // نمایش سبز برای موفقیت
        reservationDetails.classList.remove('err'); // حذف حالت خطا
        reservationDetails.innerHTML = ` // نمایش جزئیات رزرو
            <div>شناسه رزرو: <span class="highlight">${data.id}</span></div>
            <div>نام مهمان: ${data.guestName}</div>
            <div>ایمیل: ${data.guestEmail}</div>
            <div>شماره اتاق: ${data.room?.number || 'نامشخص'}</div>
            <div>بازه اقامت: ${formatDate(data.startDate)} تا ${formatDate(data.endDate)}</div>
        `;
    } catch (err) {
        reservationDetails.textContent = 'رزرو موردنظر یافت نشد یا خطایی رخ داد.'; // پیام خطا
        reservationDetails.classList.remove('ok'); // حذف حالت موفق
        reservationDetails.classList.add('err'); // نمایش قرمز
    }
}

/**
 * تبدیل رشته تاریخ ISO به قالب خوانا برای کاربر
 * @param {string} value تاریخ ورودی
 * @returns {string} تاریخ تبدیل‌شده
 */
function formatDate(value) { // فرمت کردن تاریخ برای نمایش فارسی
    if (!value) return '—'; // در صورت نبود تاریخ
    try {
        return new Date(value).toLocaleDateString('fa-IR', {year: 'numeric', month: 'short', day: '2-digit'}); // تبدیل به تاریخ شمسی/محلی
    } catch {
        return value; // در صورت خطا همان مقدار اولیه برمی‌گردد
    }
}

// اضافه کردن رویداد کلیک به دکمه جستجو
searchBtn.addEventListener('click', searchRooms); // اتصال رویداد جستجو به دکمه

// اضافه کردن رویداد کلیک به دکمه ثبت رزرو
reserveBtn.addEventListener('click', reserve); // اتصال رویداد ثبت رزرو

// رویداد پیگیری رزرو
if (reservationLookupBtn) {
    reservationLookupBtn.addEventListener('click', lookupReservation); // اتصال رویداد نمایش رزرو
}

// بارگذاری هتل‌ها هنگام لود شدن صفحه
loadHotels(); // بارگذاری اولیه داده‌های هتل


