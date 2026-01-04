/**
 * فایل JavaScript برای مدیریت رزرو پرواز
 */

const api = (path) => `${window.BACKEND_BASE_URL || 'http://localhost:8080'}${path}`;

function showError(message) {
    alert('خطا: ' + message);
}

function showSuccess(message) {
    alert('موفق: ' + message);
}

// جستجوی پرواز
async function searchFlights() {
    const originInput = document.querySelector('input[placeholder*="مبدأ"]');
    const destinationInput = document.querySelector('input[placeholder*="مقصد"]');
    const departDate = document.getElementById('departDate')?.value;
    const returnDate = document.getElementById('returnDate')?.value;
    const passengers = document.getElementById('passengers')?.value;
    const cabinClass = document.getElementById('cabinClass')?.value;

    if (!originInput || !destinationInput || !departDate) {
        showError('لطفاً مبدأ، مقصد و تاریخ رفت را وارد کنید.');
        return;
    }

    const origin = originInput.value.split('(')[0].trim();
    const destination = destinationInput.value.split('(')[0].trim();

    try {
        let url = `/api/flights/search?origin=${encodeURIComponent(origin)}&destination=${encodeURIComponent(destination)}&date=${departDate}`;
        if (returnDate) {
            url += `&returnDate=${returnDate}`;
        }
        if (cabinClass) {
            url += `&cabinClass=${encodeURIComponent(cabinClass)}`;
        }

        const response = await fetch(api(url));
        if (!response.ok) throw new Error('خطا در جستجو');

        const flights = await response.json();
        displayFlightResults(flights);
    } catch (error) {
        showError('خطا در جستجوی پروازها');
    }
}

// نمایش نتایج جستجو
function displayFlightResults(flights) {
    const resultsContainer = document.querySelector('.offers-section') || document.querySelector('.results');
    if (!resultsContainer) return;

    if (flights.length === 0) {
        resultsContainer.innerHTML = '<p>پروازی یافت نشد.</p>';
        return;
    }

    let html = '<div class="offers-grid">';
    flights.forEach(flight => {
        html += `
            <div class="offer-card">
                <div class="offer-header">
                    <h3>${flight.origin} → ${flight.destination}</h3>
                </div>
                <div class="offer-body">
                    <div class="flight-details">
                        <div class="flight-info">
                            <div class="time">${flight.departureTime || '—'}</div>
                            <div class="city">${flight.originAirport || flight.origin}</div>
                        </div>
                        <div class="flight-duration">
                            <i class="fas fa-plane"></i>
                            <div>—</div>
                        </div>
                        <div class="flight-info">
                            <div class="time">${flight.arrivalTime || '—'}</div>
                            <div class="city">${flight.destinationAirport || flight.destination}</div>
                        </div>
                    </div>
                    <div class="airline-info">
                        <div class="airline-logo">
                            <i class="fas fa-plane"></i>
                        </div>
                        <div>
                            <h4>${flight.airline || '—'}</h4>
                            <p>${flight.flightNumber || '—'}</p>
                        </div>
                    </div>
                    <div class="offer-price">${flight.price ? flight.price.toLocaleString('fa-IR') + ' تومان' : '—'}</div>
                    <button class="book-btn" onclick="reserveFlight(${flight.id})">رزرو پرواز</button>
                </div>
            </div>
        `;
    });
    html += '</div>';
    resultsContainer.innerHTML = html;
}

// رزرو پرواز
async function reserveFlight(flightId) {
    const passengerName = prompt('نام و نام خانوادگی:');
    if (!passengerName) return;

    const passengerEmail = prompt('ایمیل:');
    if (!passengerEmail) return;

    const numberOfPassengers = prompt('تعداد مسافران:', '1');
    if (!numberOfPassengers) return;

    try {
        const response = await fetch(api('/api/flights/reservations'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                flightId: flightId,
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
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', searchFlights);
    }
});

