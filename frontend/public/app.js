const $ = (s) => document.querySelector(s);
const hotelSelect = $('#hotelSelect');
const fromDate = $('#fromDate');
const toDate = $('#toDate');
const searchBtn = $('#searchBtn');
const reservationForm = $('#reservationForm');
const selectedRoomSpan = $('#selectedRoom');
const guestName = $('#guestName');
const guestEmail = $('#guestEmail');
const reserveBtn = $('#reserveBtn');
const message = $('#message');
const hotelsGrid = $('#hotelsGrid');
const roomsGrid = $('#roomsGrid');

let selectedRoomId = null;

const api = (path) => `${window.BACKEND_BASE_URL}${path}`;

async function loadHotels() {
    try {
        const res = await fetch(api('/api/hotels'));
        const data = await res.json();
        hotelSelect.innerHTML = '';
        for (const h of data) {
            const opt = document.createElement('option');
            opt.value = h.id;
            opt.textContent = `${h.name} - ${h.city}`;
            hotelSelect.appendChild(opt);
        }
        // Render recommended cards
        await renderHotelCards(data);
    } catch (e) {
        showError('خطا در دریافت لیست هتل‌ها');
    }
}

function showError(text) {
    message.textContent = text;
    message.className = 'err';
}

function showOk(text) {
    message.textContent = text;
    message.className = 'ok';
}

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
			<div class="title"><b>اتاق ${r.number}</b><span class="pill">${r.type}</span></div>
			<p class="muted">ظرفیت: ${r.capacity} نفر</p>
			<p class="price">${r.pricePerNight.toLocaleString('fa-IR')} تومان / شب</p>
			<div class="actions"><button class="btn primary" data-id="${r.id}" data-label="${r.number}">انتخاب</button></div>
		`;
        card.querySelector('button').addEventListener('click', (ev) => {
            selectedRoomId = Number(ev.currentTarget.dataset.id);
            selectedRoomSpan.textContent = ev.currentTarget.dataset.label;
            reservationForm.classList.remove('hidden');
            showOk('اتاق انتخاب شد. اطلاعات رزرو را وارد کنید.');
        });
        roomsGrid.appendChild(card);
    }
}

async function searchRooms() {
    message.textContent = '';
    const hotelId = Number(hotelSelect.value);
    const from = fromDate.value;
    const to = toDate.value;
    if (!hotelId || !from || !to) {
        showError('هتل، تاریخ شروع و پایان را وارد کنید.');
        return;
    }
    try {
        const url = api(`/api/hotels/${hotelId}/rooms?available=true&from=${from}&to=${to}`);
        const res = await fetch(url);
        if (!res.ok) throw new Error();
        const data = await res.json();
        renderRooms(data);
    } catch {
        showError('خطا در جستجوی اتاق‌ها');
    }
}

async function renderHotelCards(hotels) {
    hotelsGrid.innerHTML = '';
    for (const h of hotels) {
        // Fetch rooms to compute min price
        let minPrice = null;
        try {
            const res = await fetch(api(`/api/hotels/${h.id}/rooms`));
            if (res.ok) {
                const rooms = await res.json();
                for (const r of rooms) {
                    if (minPrice === null || r.pricePerNight < minPrice) minPrice = r.pricePerNight;
                }
            }
        } catch {
        }
        const card = document.createElement('div');
        card.className = 'card hotel-card';
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
        card.querySelector('button').addEventListener('click', async () => {
            hotelSelect.value = h.id;
            await searchRooms();
            window.scrollTo({top: document.querySelector('.results').offsetTop - 60, behavior: 'smooth'});
        });
        hotelsGrid.appendChild(card);
    }
}

async function reserve() {
    message.textContent = '';
    if (!selectedRoomId) {
        showError('ابتدا یک اتاق انتخاب کنید.');
        return;
    }
    if (!guestName.value || !guestEmail.value || !fromDate.value || !toDate.value) {
        showError('تمام فیلدها را کامل کنید.');
        return;
    }
    try {
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
        if (!res.ok) {
            const t = await res.text();
            throw new Error(t);
        }
        const data = await res.json();
        showOk(`رزرو با شناسه ${data.id} ثبت شد.`);
    } catch (e) {
        showError('خطا در ثبت رزرو');
    }
}

searchBtn.addEventListener('click', searchRooms);
reserveBtn.addEventListener('click', reserve);

loadHotels();


