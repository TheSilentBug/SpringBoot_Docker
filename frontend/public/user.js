/**
 * فایل JavaScript برای مدیریت ثبت‌نام کاربر
 */

const api = (path) => `${window.BACKEND_BASE_URL || 'http://localhost:8080'}${path}`;

function showError(message) {
    const messageEl = document.getElementById('message') || document.querySelector('.message');
    if (messageEl) {
        messageEl.textContent = message;
        messageEl.className = 'err';
    } else {
        alert('خطا: ' + message);
    }
}

function showSuccess(message) {
    const messageEl = document.getElementById('message') || document.querySelector('.message');
    if (messageEl) {
        messageEl.textContent = message;
        messageEl.className = 'ok';
    } else {
        alert('موفق: ' + message);
    }
}

// ثبت‌نام کاربر
async function registerUser(event) {
    if (event) {
        event.preventDefault();
    }

    const firstName = document.getElementById('first_name')?.value;
    const lastName = document.getElementById('last_name')?.value;
    const username = document.getElementById('user_id')?.value;
    const email = document.getElementById('email')?.value;
    const password = document.getElementById('password')?.value;
    const confirmPassword = document.getElementById('confirm_password')?.value;

    if (!firstName || !lastName || !username || !email || !password) {
        showError('تمام فیلدها را پر کنید.');
        return;
    }

    if (password !== confirmPassword) {
        showError('رمز عبور و تکرار آن مطابقت ندارند.');
        return;
    }

    if (!document.getElementById('terms')?.checked) {
        showError('لطفاً با قوانین و مقررات موافقت کنید.');
        return;
    }

    try {
        const response = await fetch(api('/api/users/register'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                username: username,
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'خطا در ثبت‌نام');
        }

        const user = await response.json();
        showSuccess(`ثبت‌نام با موفقیت انجام شد. شناسه کاربری: ${user.id}`);

        // هدایت به صفحه ورود یا صفحه اصلی
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 2000);
    } catch (error) {
        showError(error.message || 'خطا در ثبت‌نام');
    }
}

// اتصال رویداد ثبت‌نام به فرم
document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', registerUser);
    }
});

