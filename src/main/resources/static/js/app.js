// Общие константы
const USERS_API = "/user-service/v1/users";
const ADDRESSES_API = "/user-service/v1/addresses";

// Вызов методов бэка
async function api(url, options = {}) {
    const token = localStorage.getItem("token");

    const res = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
            ...(token && { Authorization: `Bearer ${token}` })
        }
    });

    if (res.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "/user-service/login.html";
        return;
    }

    return res;
}

// Вспомогательная функция для защиты от XSS
function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

// Инициализация при загрузке страницы
window.onload = function() {
    // Проверяем токен при загрузке
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/user-service/login.html";
        return;
    }
    showUsers();
};


// Очистка заполенных полей с ошибкой
function clearFieldErrors() {
    document.querySelectorAll(".input-error")
        .forEach(el => el.classList.remove("input-error"));

    document.querySelectorAll(".error-message")
        .forEach(el => el.innerText = "");
}

// подсвечивание ошибки
function applyFieldErrors(errors) {
    if (!errors) return;

    Object.keys(errors).forEach(field => {
        const input = document.getElementById(field);
        const errorDiv = document.getElementById(`${field}-error`);

        if (input) {
            input.classList.add("input-error");
        }

        if (errorDiv) {
            errorDiv.innerText = errors[field];
        }
    });
}

function showError(message) {
    console.error(message);

    // Показываем toast вместо перезагрузки страницы
    const toast = document.createElement("div");
    toast.className = "toast-error";
    toast.innerText = message;
    document.body.appendChild(toast);

    setTimeout(() => toast.remove(), 3000);
}

function showSuccess(message) {
    const toast = document.createElement("div");
    toast.className = "toast-success";
    toast.innerText = message;
    document.body.appendChild(toast);

    setTimeout(() => toast.remove(), 2000);
}