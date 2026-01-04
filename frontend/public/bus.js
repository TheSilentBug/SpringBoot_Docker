/**
 * فایل JavaScript برای مدیریت رزرو اتوبوس
 */

// تابع کمکی برای ساخت URL کامل API
const api = (path) => `${window.BACKEND_BASE_URL || 'http://localhost:8080'}${path}`;

// تابع نمایش پیام خطا
function showError(message) {
    alert('خطا: ' + message);
}

// تابع نمایش پیام موفقیت
function showSuccess(message) {
    alert('موفق: ' + message);
}

// جستجوی اتوبوس
async function searchBuses() {
    const origin = document.getElementById('bus-origin')?.value;
    const destination = document.getElementById('bus-destination')?.value;
    const date = document.getElementById('bus-date')?.value;
    const busType = document.querySelector('.bus-class-option.active')?.getAttribute('data-class');

    if (!origin || !destination || !date) {
        showError('لطفاً مبدأ، مقصد و تاریخ را وارد کنید.');
        return;
    }

    try {
        let url = `/api/buses/search?origin=${encodeURIComponent(origin)}&destination=${encodeURIComponent(destination)}&date=${date}`;
        if (busType) {
            url += `&busType=${encodeURIComponent(busType)}`;
        }

        const response = await fetch(api(url));
        if (!response.ok) throw new Error('خطا در جستجو');

        const buses = await response.json();
        displayBusResults(buses);
    } catch (error) {
        showError('خطا در جستجوی اتوبوس‌ها');
    }
}

// نمایش نتایج جستجو
function displayBusResults(buses) {
    const resultsContainer = document.querySelector('.bus-routes') || document.querySelector('.results');
    if (!resultsContainer) return;

    if (buses.length === 0) {
        resultsContainer.innerHTML = '<p>اتوبوسی یافت نشد.</p>';
        return;
    }

    let html = '<div class="routes-grid">';
    buses.forEach(bus => {
        html += `
            <div class="bus-route-card">
                <div class="bus-route-header">
                    <div class="bus-route-title">${bus.origin} - ${bus.destination}</div>
                    <div class="bus-route-price">${bus.price ? bus.price.toLocaleString('fa-IR') + ' تومان' : '—'}</div>
                </div>
                <div class="bus-route-details">
                    <div class="bus-route-time">
                        <div class="time">${bus.departureTime || '—'}</div>
                        <div class="city">${bus.origin} - ${bus.originTerminal || ''}</div>
                    </div>
                    <div class="bus-duration">—</div>
                    <div class="bus-route-time">
                        <div class="time">${bus.arrivalTime || '—'}</div>
                        <div class="city">${bus.destination} - ${bus.destinationTerminal || ''}</div>
                    </div>
                </div>
                <div class="bus-company">
                    <div class="bus-company-logo">${bus.company ? bus.company.substring(0, 2).toUpperCase() : '—'}</div>
                    <span>${bus.company || '—'}</span>
                </div>
                <div class="bus-features">
                    <span>${bus.busType || '—'}</span>
                </div>
                <div class="bus-seats">
                    <i class="fas fa-chair"></i>
                    <span>${bus.availableSeats || 0} صندلی خالی</span>
                </div>
                <button class="book-btn" onclick="reserveBus(${bus.id})">رزرو بلیط</button>
            </div>
        `;
    });
    html += '</div>';
    resultsContainer.innerHTML = html;
}

// رزرو اتوبوس
async function reserveBus(busId) {
    const passengerName = prompt('نام و نام خانوادگی:');
    if (!passengerName) return;

    const passengerEmail = prompt('ایمیل:');
    if (!passengerEmail) return;

    const numberOfPassengers = prompt('تعداد مسافران:', '1');
    if (!numberOfPassengers) return;

    try {
        const response = await fetch(api('/api/buses/reservations'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                busId: busId,
                passengerName: passengerName,
                passengerEmail: passengerEmail,
                numberOfPassengers: parseInt(numberOfPassengers)
            })
        });

        if (!response.ok) throw new Error('خطا در رزرو');

        const reservation = await response.json();
        showSuccess(`رزرو با شناسه ${reservation.id} ثبت شد.`);
    } catch (error) {
        showError('خطا در ثبت رزرو');
    }
}

// اتصال رویداد جستجو به دکمه
document.addEventListener('DOMContentLoaded', function () {
    const searchBtn = document.getElementById('search-bus');
    if (searchBtn) {
        searchBtn.addEventListener('click', searchBuses);
    }
});

