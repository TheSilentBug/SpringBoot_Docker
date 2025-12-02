// تابع کمکی برای انتخاب المان‌های DOM با استفاده از querySelector
const $ = (s) => document.querySelector(s);

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

// متغیر برای نگهداری شناسه اتاق انتخاب شده
let selectedRoomId = null;

// تابع کمکی برای ساخت URL کامل API با استفاده از آدرس بک‌اند
const api = (path) => `${window.BACKEND_BASE_URL}${path}`;

/**
 * بارگذاری لیست هتل‌ها از API و نمایش آن‌ها در صفحه
 * این تابع هتل‌ها را از سرور دریافت می‌کند و آن‌ها را در لیست کشویی و کارت‌ها نمایش می‌دهد
 */
async function loadHotels() {
    try {
        // درخواست GET به API برای دریافت لیست هتل‌ها
        const res = await fetch(api('/api/hotels'));
        const data = await res.json();
        
        // پاک کردن لیست کشویی قبل از پر کردن مجدد
        hotelSelect.innerHTML = '';
        
        // اضافه کردن هر هتل به لیست کشویی
        for (const h of data) {
            const opt = document.createElement('option');
            opt.value = h.id;
            opt.textContent = `${h.name} - ${h.city}`;
            hotelSelect.appendChild(opt);
        }
        
        // نمایش کارت‌های هتل‌ها در بخش پیشنهادی
        await renderHotelCards(data);
    } catch (e) {
        showError('خطا در دریافت لیست هتل‌ها');
    }
}

/**
 * نمایش پیام خطا به کاربر
 * @param {string} text متن پیام خطا
 */
function showError(text) {
    message.textContent = text;
    message.className = 'err'; // کلاس CSS برای نمایش خطا با رنگ قرمز
}

/**
 * نمایش پیام موفقیت به کاربر
 * @param {string} text متن پیام موفقیت
 */
function showOk(text) {
    message.textContent = text;
    message.className = 'ok'; // کلاس CSS برای نمایش موفقیت با رنگ سبز
}

/**
 * نمایش لیست اتاق‌ها در صفحه
 * این تابع کارت‌های اتاق‌ها را ایجاد می‌کند و در صفحه نمایش می‌دهد
 * @param {Array} rooms آرایه‌ای از اشیاء اتاق
 */
function renderRooms(rooms) {
    // پاک کردن محتوای قبلی
    roomsGrid.innerHTML = '';
    
    // اگر اتاقی پیدا نشد، پیام مناسب نمایش داده می‌شود
    if (!rooms.length) {
        const div = document.createElement('div');
        div.className = 'card';
        div.textContent = 'اتاقی یافت نشد';
        roomsGrid.appendChild(div);
        return;
    }
    
    // ایجاد کارت برای هر اتاق
    for (const r of rooms) {
        const card = document.createElement('div');
        card.className = 'card room-card';
        
        // ساخت HTML کارت اتاق شامل شماره، نوع، ظرفیت و قیمت
        card.innerHTML = `
			<div class="title"><b>اتاق ${r.number}</b><span class="pill">${r.type}</span></div>
			<p class="muted">ظرفیت: ${r.capacity} نفر</p>
			<p class="price">${r.pricePerNight.toLocaleString('fa-IR')} تومان / شب</p>
			<div class="actions"><button class="btn primary" data-id="${r.id}" data-label="${r.number}">انتخاب</button></div>
		`;
        
        // اضافه کردن رویداد کلیک به دکمه انتخاب اتاق
        card.querySelector('button').addEventListener('click', (ev) => {
            // ذخیره شناسه اتاق انتخاب شده
            selectedRoomId = Number(ev.currentTarget.dataset.id);
            // نمایش شماره اتاق در فرم رزرو
            selectedRoomSpan.textContent = ev.currentTarget.dataset.label;
            // نمایش فرم رزرو
            reservationForm.classList.remove('hidden');
            showOk('اتاق انتخاب شد. اطلاعات رزرو را وارد کنید.');
        });
        
        // اضافه کردن کارت به صفحه
        roomsGrid.appendChild(card);
    }
}

/**
 * جستجوی اتاق‌های موجود یک هتل در بازه زمانی مشخص
 * این تابع با استفاده از API، اتاق‌های موجود را پیدا می‌کند و نمایش می‌دهد
 */
async function searchRooms() {
    // پاک کردن پیام‌های قبلی
    message.textContent = '';
    
    // دریافت مقادیر از فرم
    const hotelId = Number(hotelSelect.value);
    const from = fromDate.value;
    const to = toDate.value;
    
    // بررسی اینکه تمام فیلدها پر شده‌اند
    if (!hotelId || !from || !to) {
        showError('هتل، تاریخ شروع و پایان را وارد کنید.');
        return;
    }
    
    try {
        // ساخت URL برای درخواست API با پارامترهای جستجو
        const url = api(`/api/hotels/${hotelId}/rooms?available=true&from=${from}&to=${to}`);
        const res = await fetch(url);
        
        // بررسی موفقیت درخواست
        if (!res.ok) throw new Error();
        
        // دریافت داده‌های اتاق‌ها
        const data = await res.json();
        
        // نمایش اتاق‌های پیدا شده
        renderRooms(data);
    } catch {
        showError('خطا در جستجوی اتاق‌ها');
    }
}

/**
 * نمایش کارت‌های هتل‌ها در بخش پیشنهادی
 * این تابع برای هر هتل یک کارت ایجاد می‌کند و کمترین قیمت اتاق را محاسبه می‌کند
 * @param {Array} hotels آرایه‌ای از اشیاء هتل
 */
async function renderHotelCards(hotels) {
    // پاک کردن محتوای قبلی
    hotelsGrid.innerHTML = '';
    
    // ایجاد کارت برای هر هتل
    for (const h of hotels) {
        // دریافت لیست اتاق‌ها برای محاسبه کمترین قیمت
        let minPrice = null;
        try {
            const res = await fetch(api(`/api/hotels/${h.id}/rooms`));
            if (res.ok) {
                const rooms = await res.json();
                // پیدا کردن کمترین قیمت در بین تمام اتاق‌ها
                for (const r of rooms) {
                    if (minPrice === null || r.pricePerNight < minPrice) minPrice = r.pricePerNight;
                }
            }
        } catch {
            // در صورت خطا، minPrice همان null باقی می‌ماند
        }
        
        // ایجاد المان کارت هتل
        const card = document.createElement('div');
        card.className = 'card hotel-card';
        
        // ساخت HTML کارت شامل نام، شهر، آدرس و کمترین قیمت
        card.innerHTML = `
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
        card.querySelector('button').addEventListener('click', async () => {
            // انتخاب هتل در لیست کشویی
            hotelSelect.value = h.id;
            // جستجوی اتاق‌های این هتل
            await searchRooms();
            // اسکرول به بخش نتایج
            window.scrollTo({top: document.querySelector('.results').offsetTop - 60, behavior: 'smooth'});
        });
        
        // اضافه کردن کارت به صفحه
        hotelsGrid.appendChild(card);
    }
}

/**
 * ثبت رزرو جدید
 * این تابع اطلاعات رزرو را به API ارسال می‌کند و رزرو را ثبت می‌کند
 */
async function reserve() {
    // پاک کردن پیام‌های قبلی
    message.textContent = '';
    
    // بررسی اینکه اتاقی انتخاب شده باشد
    if (!selectedRoomId) {
        showError('ابتدا یک اتاق انتخاب کنید.');
        return;
    }
    
    // بررسی اینکه تمام فیلدهای فرم پر شده باشند
    if (!guestName.value || !guestEmail.value || !fromDate.value || !toDate.value) {
        showError('تمام فیلدها را کامل کنید.');
        return;
    }
    
    try {
        // ارسال درخواست POST به API برای ایجاد رزرو
        const res = await fetch(api('/api/reservations'), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                roomId: selectedRoomId,
                guestName: guestName.value,
                guestEmail: guestEmail.value,
                startDate: fromDate.value,
                endDate: toDate.value
            })
        });
        
        // بررسی موفقیت درخواست
        if (!res.ok) {
            const t = await res.text();
            throw new Error(t);
        }
        
        // دریافت پاسخ از سرور
        const data = await res.json();
        
        // نمایش پیام موفقیت با شناسه رزرو
        showOk(`رزرو با شناسه ${data.id} ثبت شد.`);
    } catch (e) {
        showError('خطا در ثبت رزرو');
    }
}

// اضافه کردن رویداد کلیک به دکمه جستجو
searchBtn.addEventListener('click', searchRooms);

// اضافه کردن رویداد کلیک به دکمه ثبت رزرو
reserveBtn.addEventListener('click', reserve);

// بارگذاری هتل‌ها هنگام لود شدن صفحه
loadHotels();


