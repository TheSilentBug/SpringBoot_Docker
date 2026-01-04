/**
 * فایل JavaScript برای مدیریت رزرو قطار
 */

const api = (path) => `${window.BACKEND_BASE_URL || 'http://localhost:8080'}${path}`;

function showError(message) {
    alert('خطا: ' + message);
}

function showSuccess(message) {
    alert('موفق: ' + message);
}

// جستجوی قطار
async function searchTrains() {
    const origin = document.getElementById('origin')?.value;
    const destination = document.getElementById('destination')?.value;
    const date = document.getElementById('travel-date')?.value;
    const trainClass = document.querySelector('.class-option.active')?.getAttribute('data-class');

    if (!origin || !destination || !date) {
        showError('لطفاً مبدأ، مقصد و تاریخ را وارد کنید.');
        return;
    }

    try {
        let url = `/api/trains/search?origin=${encodeURIComponent(origin)}&destination=${encodeURIComponent(destination)}&date=${date}`;
        if (trainClass) {
            url += `&trainClass=${encodeURIComponent(trainClass)}`;
        }

        const response = await fetch(api(url));
        if (!response.ok) throw new Error('خطا در جستجو');

        const trains = await response.json();
        displayTrainResults(trains);
    } catch (error) {
        showError('خطا در جستجوی قطارها');
    }
}

// نمایش نتایج جستجو
function displayTrainResults(trains) {
    const resultsContainer = document.querySelector('.popular-routes') || document.querySelector('.results');
    if (!resultsContainer) return;

    if (trains.length === 0) {
        resultsContainer.innerHTML = '<p>قطاری یافت نشد.</p>';
        return;
    }

    let html = '<div class="routes-grid">';
    trains.forEach(train => {
        html += `
            <div class="route-card">
                <h3>${train.origin} - ${train.destination}</h3>
                <div class="route-details">
                    <div>
                        <div>${train.origin}</div>
                        <small>${train.departureTime || '—'}</small>
                    </div>
                    <div class="time-badge">—</div>
                    <div>
                        <div>${train.destination}</div>
                        <small>${train.arrivalTime || '—'}</small>
                    </div>
                </div>
                <div class="route-stops">
                    <i class="fas fa-map-marker-alt"></i>
                    <span>${train.stops || 'بدون توقف'}</span>
                </div>
                <div class="train-features">
                    <span>${train.trainClass || '—'}</span>
                    <span>${train.trainName || '—'}</span>
                </div>
                <div class="route-price">${train.price ? train.price.toLocaleString('fa-IR') + ' تومان' : '—'}</div>
                <button class="book-btn" onclick="reserveTrain(${train.id})">رزرو بلیط</button>
            </div>
        `;
    });
    html += '</div>';
    resultsContainer.innerHTML = html;
}

// رزرو قطار
async function reserveTrain(trainId) {
    const passengerName = prompt('نام و نام خانوادگی:');
    if (!passengerName) return;

    const passengerEmail = prompt('ایمیل:');
    if (!passengerEmail) return;

    const numberOfPassengers = prompt('تعداد مسافران:', '1');
    if (!numberOfPassengers) return;

    try {
        const response = await fetch(api('/api/trains/reservations'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                trainId: trainId,
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

// اتصال رویداد جستجو
document.addEventListener('DOMContentLoaded', function () {
    const searchBtn = document.getElementById('search-trains');
    if (searchBtn) {
        searchBtn.addEventListener('click', searchTrains);
    }
});

